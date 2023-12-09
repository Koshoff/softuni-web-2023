document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll("[id^='commentForm-']").forEach(function(form){


        form.addEventListener("submit", function(event) {
            event.preventDefault();

            const formData = new FormData(form);
            const articleId = formData.get("articleId");
            const comment = formData.get("comment");



            const data = {
                articleId: articleId,
                content: comment,
            };

            fetch(`/post-comment/${articleId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data)
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                console.log('Comment posted successfully');
                form.reset();
                // Допълнителна логика при успешно изпращане
            })
            .catch(error => {
                console.error('Error:', error);
            });
        });

    });

});