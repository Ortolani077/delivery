$(document).ready(function () {
    const deliveryFee = 5.00;
    let cart = [];

    // Configuração do carrossel
    $('.carousel').slick({
        infinite: true,
        slidesToShow: 3,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 2000,
        dots: true,
    });

    // Carregar pizzas do banco de dados
    function loadPizzasFromDB() {
        $.ajax({
            url: '/delivery/produtos',
            method: 'GET',
            dataType: 'json',
            success: function (pizzas) {
                pizzas.forEach(pizza => {
                    $('#pizzas-list').append(`
                        <div class="pizza-item col-md-4" data-id="${pizza.id}" data-name="${pizza.nome}" data-price="${pizza.preco}">
                            <h5>${pizza.nome}</h5>
                            <p>R$ ${pizza.preco.toFixed(2)}</p>
                            <p>${pizza.descricao}</p>
                            <button class="btn btn-primary add-to-cart">Adicionar ao Carrinho</button>
                        </div>
                    `);
                });
            },
            error: function (xhr, status, error) {
                console.error('Erro ao carregar as pizzas:', error);
            }
        });
    }

    loadPizzasFromDB();

    // Adicionar ao carrinho
    $(document).on('click', '.add-to-cart', function () {
        const id = $(this).closest('.pizza-item').data('id');
        const name = $(this).closest('.pizza-item').data('name');
        const price = $(this).closest('.pizza-item').data('price');

        const existingItem = cart.find(item => item.id === id);
        if (existingItem) {
            existingItem.quantity += 1;
        } else {
            cart.push({ id, name, price, quantity: 1 });
        }

        updateCart();
    });

    // Atualizar carrinho
    function updateCart() {
        $('#cart-items').empty();
        let total = 0;

        cart.forEach(item => {
            const itemTotal = item.price * item.quantity;
            total += itemTotal;

            $('#cart-items').append(`
                <div class="cart-item">
                    <p>${item.name} x${item.quantity} - R$ ${itemTotal.toFixed(2)}</p>
                    <button class="btn btn-sm btn-danger remove-from-cart" data-id="${item.id}">Remover</button>
                </div>
            `);
        });

        $('#cart-total').text(total.toFixed(2));
        updateFinalTotal();
    }

    // Atualizar o total final
    function updateFinalTotal() {
        let finalTotal = parseFloat($('#cart-total').text());
        if ($('#deliveryOption').val() === 'entrega') {
            finalTotal += deliveryFee;
            $('#delivery-fee').show();
        } else {
            $('#delivery-fee').hide();
        }
        $('#final-total').text(finalTotal.toFixed(2));
    }

    // Remover do carrinho
    $(document).on('click', '.remove-from-cart', function () {
        const id = $(this).data('id');
        cart = cart.filter(item => item.id !== id);
        updateCart();
    });

    // Limpar carrinho
    $('#clear-cart-btn').on('click', function () {
        cart = [];
        updateCart();
    });

    // Finalizar compra
    $('#checkout-form').on('submit', function (e) {
        e.preventDefault();

        const clientName = $('#clientName').val();
        const clientPhone = $('#clientPhone').val();
        const clientEmail = $('#clientEmail').val();
        const clientAddress = $('#clientAddress').val();
        const houseNumber = $('#houseNumber').val();
        const complement = $('#complement').val();
        const reference = $('#reference').val();
        const deliveryOption = $('#deliveryOption').val();
        const paymentMethod = $('#paymentMethod').val();
        const observacoes = $('#observacoes').val();
        const finalTotal = parseFloat($('#final-total').text());

        let troco = null;
        if (paymentMethod === 'dinheiro') {
            const changeGiven = parseFloat($('#changeGiven').val());
            if (!isNaN(changeGiven) && changeGiven >= finalTotal) {
                troco = changeGiven - finalTotal;
                $('#change').text(troco.toFixed(2));
                $('#change-output').show();
            } else {
                $('#change-output').hide();
                alert('O valor recebido não é suficiente ou inválido.');
                return;
            }
        } else {
            $('#change-output').hide();
        }

        const paymentMethodText = {
            cartao: 'Cartão de Crédito/Débito',
            dinheiro: 'Dinheiro',
            pix: 'PIX na Entrega'
        }[paymentMethod] || 'Método de pagamento não especificado';

        const pedidoDTO = {
            emailCliente: clientEmail,
            nomeCliente: clientName,
            telefoneCliente: clientPhone,
            enderecoCliente: `${clientAddress}, ${houseNumber}, ${complement}, ${reference}`,
            entrega: deliveryOption === 'entrega',
            preco: finalTotal,
            observacoes: `${observacoes} Pagamento: ${paymentMethodText}${paymentMethod === 'dinheiro' ? `; Troco a ser devolvido R$ ${troco ? troco.toFixed(2) : ''}` : ''}`,
            itens: cart.map(item => ({
                produtoId: item.id,
                quantidade: item.quantity
            }))
        };

        console.log('Enviando pedidoDTO:', pedidoDTO);

        $.ajax({
            url: 'http://localhost:8081/delivery/pedidos/criar',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(pedidoDTO),
            success: function (response) {
                console.log('Resposta do servidor:', response);
                alert('Pedido realizado com sucesso!');
                $('#checkoutModal').modal('hide');
                cart = [];
                updateCart();

                // Verificar o dia da semana para calcular o tempo estimado de entrega
                const currentDay = new Date().getDay(); // 0 é domingo, 1 é segunda, ..., 6 é sábado
                let deliveryTimeRange;

                if (currentDay >= 1 && currentDay <= 4) { // Segunda a quinta-feira
                    deliveryTimeRange = "45 a 60 minutos";
                } else { // Sexta a domingo
                    deliveryTimeRange = "60 a 120 minutos";
                }

                // Mostrar a tela de tempo estimado de entrega
                $('#delivery-time-modal .modal-body').text(`O tempo estimado de entrega é de ${deliveryTimeRange}.`);
                $('#delivery-time-modal').modal('show');
            },
            error: function (xhr, status, error) {
                console.error('Erro ao realizar o pedido:', error);
                console.log('Status:', status);
                console.log('Resposta:', xhr.responseText);
                alert('Ocorreu um erro ao realizar o pedido.');
            }
        });
    });

    // Mostrar campo de troco para pagamento em dinheiro
    $('#paymentMethod').on('change', function () {
        if ($(this).val() === 'dinheiro') {
            $('#change-container').show();
        } else {
            $('#change-container').hide();
            $('#change-output').hide();
        }
        updateFinalTotal();
    });

    // Atualizar o total final quando o carrinho for atualizado
    $(document).on('click', '.add-to-cart, .remove-from-cart', function () {
        updateFinalTotal();
    });

    // Fechar modais ao clicar fora deles
    $(document).on('click', function (event) {
        // Verifica se o clique foi fora do modal de pizzas
        if (!$(event.target).closest('#pizza-modal').length && !$(event.target).closest('.open-pizza-modal').length) {
            $('#pizza-modal').modal('hide');
        }

        // Verifica se o clique foi fora do modal de bebidas
        if (!$(event.target).closest('#bebida-modal').length && !$(event.target).closest('.open-bebida-modal').length) {
            $('#bebida-modal').modal('hide');
        }

        // Verifica se o clique foi fora do modal de esfiha
        if (!$(event.target).closest('#esfiha-modal').length && !$(event.target).closest('.open-esfiha-modal').length) {
            $('#esfiha-modal').modal('hide');
        }

        if (!$(event.target).closest('#cart-container, .add-to-cart, .pizza-item').length) {
            // Fechar o carrinho se o clique não for dentro do carrinho ou em um botão de adicionar ao carrinho
            $('#cart-container').hide();
        }

        if (!$(event.target).closest('#deliveryOption, #paymentMethod, #change-container').length) {
            // Fechar as listas de opções de entrega e método de pagamento se o clique for fora delas
            $('#deliveryOption').blur();
            $('#paymentMethod').blur();
            $('#change-container').hide();
        }
    });

    // Mostrar o carrinho novamente quando for atualizado
    $(document).on('click', '.add-to-cart', function () {
        $('#cart-container').show();
    });

    // Exibir o modal de pizzas
    $('.open-pizza-modal').on('click', function () {
        $('#pizza-modal').modal('show');
    });

    // Exibir o modal de bebidas
    $('.open-bebida-modal').on('click', function () {
        $('#bebida-modal').modal('show');
    });

    // Exibir o modal de esfihas
    $('.open-esfiha-modal').on('click', function () {
        $('#esfiha-modal').modal('show');
    });
});
