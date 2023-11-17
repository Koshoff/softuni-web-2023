function toggleComments(buttonElement) {
    // Намиране на близкия коментар контейнер
    var commentsContainer = buttonElement.nextElementSibling;

    // Проверка дали коментарите са вече показани
    if (commentsContainer.style.display === "none") {
        commentsContainer.style.display = "block";
        buttonElement.textContent = "Скрий коментари";
    } else {
        commentsContainer.style.display = "none";
        buttonElement.textContent = "Покажи коментари";
    }
}