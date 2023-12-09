document.addEventListener('DOMContentLoaded', function () {

    document.querySelectorAll('.show-comments-btn').forEach(function (button) {
        button.addEventListener('click', function () {

            var articleId = this.getAttribute('data-article-id');
            loadComments(articleId);
        });
    });
});

function loadComments(articleId) {
    fetch(`/comments?articleId=${articleId}`)
        .then(response => {

            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(comments => {
          console.log("Comments received:", comments);
            const commentsContainer = document.getElementById(`commentsContainer-${articleId}`);
            console.log("Comments container found:", commentsContainer);
            if (!commentsContainer) {
                throw new Error(`Коментарният контейнер не беше намерен за articleId: ${articleId}`);
            }
            console.log('vsichko6');
            commentsContainer.innerHTML = '';
            comments.forEach(comment => {

                            const commentElement = document.createElement('div');
                            commentElement.className = 'comment';

                            const userNameElement = document.createElement('span');
                            userNameElement.className = 'comment-user-name';
                            userNameElement.textContent = comment.authorName + ': ';

                            commentElement.appendChild(userNameElement);


                                const contentElement = document.createElement('span');
                                contentElement.textContent = comment.content;
                                commentElement.appendChild(contentElement);
                                commentsContainer.appendChild(commentElement);


                        });

        })
        .catch(error => {
            console.error('Error fetching comments:', error);
        });
}

document.querySelectorAll('.show-comments-btn').forEach(button => {
    button.addEventListener('click', () => {
        const articleId = button.getAttribute('data-article-id');
        loadComments(articleId);
    });
});