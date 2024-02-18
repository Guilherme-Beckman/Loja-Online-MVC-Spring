
    const adicionarAoCarrinho = (userid, productId) => {
        fetch('/api/cart/products/' + userid + productId, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
        })
        .then(response => response.json())
        .then(data => {
            alert('Produto adicionado ao carrinho com sucesso!');
            // Você pode realizar ações adicionais após adicionar ao carrinho, se necessário
        })
        .catch(error => {
            console.error('Erro ao adicionar produto ao carrinho:', error);
        });
    };
