$(document).ready(function() {
    fetchAllPosts();
    $("#newPostButton").click(showNewPostForm);
    $("#post-form").submit(submitPost);
    $("#comment-form").submit(submitComment);
    $("#backButton").click(showAllPosts);
    $("#backButton").css({
        position: 'absolute',
        top: '10px',
        left: '10px'
    });
});

let currentPost = null;

function fetchAllPosts() {
    $.getJSON('/api/posts')
        .done(posts => {
            $('#posts-links').empty();
            posts.forEach(addPostToList);
        })
        .fail(error => console.error('An error occurred:', error));
}

function addPostToList(post) {
    const postElement = $('<div>').addClass('post-item');
    postElement.append(
        $('<h2>').text(post.title).click(() => showPostDetails(post)),
        $('<p>').text(`Author: ${post.author}`),
        $('<p>').text(`Date: ${new Date(post.date).toLocaleDateString()}`),
        $('<p>').text(post.content),
        $('<hr>')
    );
    $('#posts-links').append(postElement);
}

function showPostDetails(post) {
    currentPost = post;
    $('#allPosts').hide();
    $("#newPostButton").hide();
    $("#newPostForm").hide();
    $("#postDetails").show();
    $('#detailsDiv').empty();
    $('#commentsDiv').empty();
    $("#backButton").show();

    $('#detailsDiv').append(
        $('<h1>').text(post.title),
        $('<p>').text(`Author: ${post.author}`),
        $('<p>').text(`Date: ${new Date(post.date).toLocaleDateString()}`),
        $('<p>').text(post.content)
    );

    fetchComments(post);
}

function fetchComments(post) {
    $.getJSON(`/api/comments/${post.id}`)
        .done(comments => {
            comments.forEach(comment => {
                $('#commentsDiv').append(
                    $('<h4>').text(comment.author),
                    $('<p>').text(comment.comment),
                    $('<hr>')
                );
            });
        })
        .fail(error => console.error('An error occurred:', error));
}

function showNewPostForm() {
    $("#newPostForm").show();
    $("#allPosts").hide();
    $("#postDetails").hide();
    $("#backButton").show();

    $('#postAuthor').val('');
    $('#postTitle').val('');
    $('#postContent').val('');
}

function showAllPosts() {
    $("#postDetails").hide();
    $("#allPosts").show();
    $("#newPostButton").show();
    $("#backButton").hide();

    $('#postAuthor').val('');
    $('#postTitle').val('');
    $('#postContent').val('');

    $('#commentName').val('');
    $('#commentContent').val('');
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
                addPostToList(post);
                $("#newPostForm").hide();
                $("#allPosts").show();
                $("#newPostButton").show();
            })
            .fail(error => console.error('An error occurred:', error));
    } else {
        alert('All fields are required');
    }
}

function submitComment(event) {
    event.preventDefault();
    const name = $('#commentName').val();
    const comment = $('#commentContent').val();
    if (name && comment) {
        $.ajax({
            url: `/api/comments/${currentPost.id}`,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ author: name, comment: comment, postId: currentPost.id })
        })
            .done(() => {
                const newComment = { author: name, comment: comment };
                $('#commentsDiv').append(
                    $('<h4>').text(newComment.author),
                    $('<p>').text(newComment.comment),
                    $('<hr>')
                );
                $('#commentName').val('');
                $('#commentContent').val('');
            })
            .fail(error => {
                console.error('An error occurred:', error);
                console.log(error.responseText);
            });
    } else {
        alert('All fields are required');
    }
}