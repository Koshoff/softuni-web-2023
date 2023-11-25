document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector("form");
    
    form.addEventListener("submit", function(event) {
        event.preventDefault();

        const formData = new FormData(form);
        const content = formData.get("comment");

        if (!content.trim()) {
            alert("Please, leave a comment.");
            return;
        }

        const articleId = formData.get("articleId");

        const data = {
            content: content,
            articleId: articleId

        };

        fetch(`/post-comment/${articleId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("Comment posted successfully.");
                form.reset(); // Ресетиране на формата след успешно изпращане
            } else {
                alert("Error while posting comment: " + data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("Error while posting comment.");
        });
    });
});
