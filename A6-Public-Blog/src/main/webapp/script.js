$(document).ready(function() {
    initializePage();
});

let currentPost = null;

function initializePage() {
    $("#registerButton").click(showRegisterForm);
    $("#loginButton").click(showLoginForm);
    $("#logoutButton").click(logout);

    $("#register-form").submit(register);
    $("#login-form").submit(login);
    $("#post-form").submit(submitPost);
    $("#comment-form").submit(submitComment);

    toggleAuthButtons();
}

function toggleAuthButtons() {
    const jwt = localStorage.getItem('jwt');
    if (jwt) {
        $("#newPostButton").show();
        $("#logoutButton").show();
        $("#registerButton, #loginButton").hide();
        fetchAllPosts();
    } else {
        $("#newPostButton, #logoutButton").hide();
        $("#registerButton, #loginButton").show();
        $('#allPosts').hide();
    }
}

function showRegisterForm() {
    hideAllForms();
    $("#registerForm").show();
    clearInputFields();
}

function showLoginForm() {
    hideAllForms();
    $("#loginForm").show();
    clearInputFields();
}

function hideAllForms() {
    $(".auth-form").hide();
    $(".content-form").hide();
}

function clearInputFields() {
    $('input[type="text"], input[type="password"], textarea').val('');
}

function showNewPostForm() {
    hideAllForms();
    $('#allPosts').hide();
    $('#newPostForm').show();
    $('#detailsDiv').remove();
    $('#commentsDiv').remove();

    $('#backButton').remove();
    populateAuthorField();
}



function populateAuthorField() {
    const jwt = localStorage.getItem('jwt');
    if (jwt) {
        const username = parseJwt(jwt).sub;
        $('#postAuthor').val(username);
    }
}

function populateCommentNameField() {
    const jwt = localStorage.getItem('jwt');
    if (jwt) {
        const username = parseJwt(jwt).sub;
        $('#commentName').val(username);
    }
}

function fetchAllPosts() {
    $('#posts-links').empty();
    $.getJSON('/api/posts')
        .done(posts => {
            posts.forEach(addPost)
            $('#allPosts').show();
        })
        .fail(error => console.error('An error occurred:', error));
}

function addPost(post) {
    const postLinks = $('#posts-links');
    const postElement = $('<div>');
    postElement.append(
        $('<h2>').text(post.title),
        $('<p>').text(post.author),
        $('<p>').text(new Date(post.date).toLocaleDateString()),
        $('<p>').text(post.content),
        $('<button>').text('Details').click(() => showPostDetails(post)),
        $('<br><br>')
    );
    postLinks.append(postElement);
}
function showPostDetails(post) {
    currentPost = post;
    $('#allPosts').hide();
    $("#newPostButton").hide();

    const detailsDiv = $('<div id="detailsDiv">');
    detailsDiv.append(
        $('<h1>').text(post.title),
        $('<p>').text(post.author),
        $('<p>').text(post.content)
    );

    const newCommentForm = $('#newCommentForm').show();
    const commentsHeader = $('<h2 id="commentsHeader">').text("Comments");
    const commentsDiv = $('<div id="commentsDiv">');

    $('#container').append(detailsDiv, newCommentForm, commentsHeader, commentsDiv);
    fetchComments(post);

    const backButton = $('<button id="backButton" class="btn btn-secondary">').text('Back').click(hidePostDetails);
    backButton.css({
        position: 'absolute',
        top: '10px',
        left: '10px'
    });
    $('#container').append(backButton);

    populateCommentNameField();
}


function hidePostDetails() {
    $('#detailsDiv').remove();
    $('#backButton').remove();
    $('#commentsDiv').remove();
    $('#commentsHeader').remove();
    $('#newCommentForm').hide();
    $('#allPosts').show();
    $("#newPostButton").show();
}


function fetchComments(post) {
    $.getJSON(`/api/comments/${post.id}`)
        .done(comments => {
            const commentsDiv = $('#commentsDiv');
            comments.forEach(comment => {
                commentsDiv.append(
                    $('<h3>').text(comment.author),
                    $('<p>').text(comment.comment)
                );
            });
        })
        .fail(error => console.error('An error occurred:', error));
}

function submitComment(event) {
    event.preventDefault();
    const name = $('#commentName').val();
    const comment = $('#commentContent').val();
    if (name && comment) {
        const postId = currentPost.id;
        const jwt = localStorage.getItem('jwt');
        const username = parseJwt(jwt).sub;
        $.ajax({
            url: `/api/comments/${postId}`,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ author: username, comment: comment, postId: postId })
        })
            .done(() => {
                $('#commentsDiv').append($('<h3>').text(username), $('<p>').text(comment));
                $('#commentContent').val('');
            })
            .fail(error => console.error('An error occurred:', error));
    } else {
        alert('All fields are required');
    }
}

function register(event) {
    event.preventDefault();
    const username = $('#registerUsername').val();
    const password = $('#registerPassword').val();
    if (username && password) {
        $.ajax({
            url: '/api/users/register',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ username, password })
        })
            .done(data => {
                localStorage.setItem('jwt', data.jwt);
                alert('Registration successful');
                hideAllForms();
                toggleAuthButtons();
                fetchAllPosts();
            })
            .fail(error => alert(error.responseJSON.message));
    } else {
        alert('All fields are required');
    }
}

function login(event) {
    event.preventDefault();
    const username = $('#username').val();
    const password = $('#password').val();
    if (username && password) {
        $.ajax({
            url: '/api/users/login',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ username, password })
        })
            .done(data => {
                localStorage.setItem('jwt', data.jwt);
                hideAllForms();
                toggleAuthButtons();
                fetchAllPosts();
            })
            .fail(error => console.error('An error occurred:', error));
    } else {
        alert('All fields are required');
    }
}

function submitPost(event) {
    event.preventDefault();
    const author = $('#postAuthor').val();
    const title = $('#postTitle').val();
    const content = $('#postContent').val();
    if (author && title && content) {
        $.ajax({
            url: '/api/posts',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ author, title, content, date: new Date() })
        })
            .done(post => {
                addPost(post);
                hideAllForms();
                $('#allPosts').show();
            })
            .fail(error => console.error('An error occurred:', error));
    } else {
        alert('All fields are required');
    }
}

function logout() {
    localStorage.removeItem('jwt');
    $('#allPosts').hide();
    hideAllForms();
    toggleAuthButtons();
    $('#detailsDiv').remove();
    $('#backButton').remove();
    $('#commentsDiv').remove();
    $('#commentsHeader').remove();
    $('#newCommentForm').hide();
}

function parseJwt(token) {
    if (!token) {
        console.error('Token is undefined');
        return;
    }
    const base64Url = token.split('.')[1];
    if (!base64Url) {
        console.error('Token is not properly formatted');
        return;
    }
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(c => `%${('00' + c.charCodeAt(0).toString(16)).slice(-2)}`).join(''));
    return JSON.parse(jsonPayload);
}
