document.addEventListener('DOMContentLoaded', function () {
    // Намиране на всички бутони за показване на коментари
    document.querySelectorAll('.show-comments-btn').forEach(function (button) {
        button.addEventListener('click', function () {
            // Вземане на article ID от data атрибута
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
            const commentsContainer = document.getElementById(`commentsContainer-${articleId}`);
            if (!commentsContainer) {
                throw new Error(`Коментарният контейнер не беше намерен за articleId: ${articleId}`);
            }
            commentsContainer.innerHTML = ''; // Изчистване на съдържанието

            // Проверете дали има коментари
            if (comments.length === 0) {
                commentsContainer.innerHTML = '<p>Няма коментари за тази статия.</p>';
            } else {
                comments.forEach(comment => {
                    const commentElement = document.createElement('div');
                    commentElement.className = 'comment';
                    commentElement.textContent = comment.content; // Примерно, зависи от структурата на вашия CommentDTO
                    commentsContainer.appendChild(commentElement);
                });
            }
        })
        .catch(error => {
            console.error('Error fetching comments:', error);
        });
}