function loadMostLikedArticles() {
    fetch('/api/articles/most-liked')
        .then(response => response.json())
        .then(data => {
            // Обработка на данните
            displayArticles(data);
        })
        .catch(error => console.error('Error:', error));
}

function displayArticles(articles) {
    // Логика за визуализиране на статиите във вашия фронтенд
    const articlesContainer = document.getElementById('articles-container');
        articlesContainer.innerHTML = ''; // Изчистване на съществуващото съдържание

        articles.forEach(article => {
            const articleElement = document.createElement('div');
            articleElement.innerHTML = `
                <h3>${article.title}</h3>
                <p>${article.summary}</p>
                <!-- Допълнителни детайли за статията -->
            `;
            articlesContainer.appendChild(articleElement);
        });
}

// Зареждане на статиите при стартиране на страницата
loadMostLikedArticles();