   document.querySelectorAll('.like-button').forEach(button => {
    button.addEventListener('click', function() {
        const articleId = this.getAttribute('data-article-id');
        // Изпращане на заявка към сървъра
        fetch(`/like-article/${articleId}`, { method: 'POST' })
            .then(response => {
                if (response.ok) {
                    console.log('Article liked!');
                }
            });
    });
});