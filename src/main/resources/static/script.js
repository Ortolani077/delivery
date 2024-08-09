document.addEventListener("DOMContentLoaded", function() {
    const galleryItems = document.querySelectorAll(".gallery__item");
    const lightbox = document.getElementById("lightbox");
    const lightboxImg = lightbox.querySelector("img");

    // Função para ajustar o tamanho da galeria
    function adjustGallery() {
        document.querySelectorAll(".gallery__item").forEach(item => {
            item.style.width = 'calc(33.333% - 10px)'; // Ajuste o tamanho dos itens da galeria
        });
    }

    // Configura a galeria para abrir o lightbox
    galleryItems.forEach(item => {
        item.addEventListener("click", () => {
            const imgSrc = item.querySelector(".gallery__img").src;
            lightboxImg.src = imgSrc;
            lightbox.style.display = "flex";
        });
    });

    // Fecha o lightbox ao clicar fora da imagem
    lightbox.addEventListener("click", () => {
        lightbox.style.display = "none";
    });

    // Ajusta a galeria no carregamento e redimensionamento da janela
    adjustGallery();
    window.addEventListener("resize", adjustGallery);

    // Inicializa o carrossel
    $(".carousel").slick({
        dots: true,
        infinite: true,
        slidesToShow: 4,
        slidesToScroll: 4,
        autoplay: true,
        autoplaySpeed: 2000
    });

    // Inicializa o Materialize
    M.AutoInit();

    // Lógica para abrir e fechar seções expandidas
    document.addEventListener("click", function(e) {
        if (!e.target.closest(".expanded-content") && !e.target.closest(".custom-btn")) {
            document.querySelectorAll(".expanded-content").forEach(content => {
                content.classList.remove("show");
            });
        }
    });

    document.querySelectorAll(".custom-btn").forEach(button => {
        button.addEventListener("click", function() {
            const targetId = this.getAttribute("data-bs-target");
            const targetElement = document.querySelector(targetId);

            if (targetElement) {
                // Alterna a visibilidade do conteúdo
                targetElement.classList.toggle("show");
            }
        });
    });

    // Função para carregar pizzas via AJAX
    function loadPizzas() {
        $.ajax({
            url: 'http://localhost:8081/delivery/produtos', // Ajuste o URL conforme necessário
            method: 'GET',
            success: function(data) {
                console.log('Dados recebidos:', data); // Verifica o conteúdo dos dados recebidos
                const pizzasList = document.getElementById('pizzas-list');
                pizzasList.innerHTML = ''; // Limpa o conteúdo atual

                if (Array.isArray(data)) { // Verifica se os dados são uma lista
                    data.forEach(pizza => {
                        const pizzaItem = document.createElement('div');
                        pizzaItem.classList.add('pizza-item');
                        pizzaItem.innerHTML = `
                            <h3>${pizza.nome}</h3>
                            <p>${pizza.descricao}</p>
                            <p>R$ ${pizza.preco.toFixed(2)}</p>
                        `;
                        pizzasList.appendChild(pizzaItem);
                    });
                } else {
                    pizzasList.innerHTML = '<p>Erro ao carregar pizzas. Dados inválidos.</p>';
                }
            },
            error: function(err) {
                console.error('Erro ao carregar pizzas:', err);
                document.getElementById('pizzas-list').innerHTML = '<p>Erro ao carregar pizzas.</p>';
            }
        });
    }

    // Carregar pizzas ao abrir a seção
    document.getElementById('collapseWhoWeAre').addEventListener('show.bs.collapse', loadPizzas);
});
