document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("opinionForm");

    form.addEventListener("submit", function(event) {
        event.preventDefault();

        const formData = new FormData(form);
        const postId = document.getElementById("postId").value;
        const content = form.querySelector("textarea[name='content']").value;



        const data = {
            postId: postId,
            content: content,
        };
                console.log("Sending data:", JSON.stringify(data));


        fetch(`/blog/${postId}/leave-opinion`, {
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
            console.log('Opinion posted successfully');

        })
        .catch(error => {
            console.error('Error:', error);
        });
    });
});