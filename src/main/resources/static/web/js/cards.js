Vue.createApp({

    data() {
        return {
            clientInfo: {},
            creditCards: [],
            debitCards: [],
            errorToats: null,
            errorMsg: null,
            cardsTrue:[],
            cardsTrueCredit:[],
            f: new Date()
        }
    },
    methods: {
        getData: function () {
            axios.get("/api/clients/current")
                .then((response) => {
                    //get client ifo
                    this.clientInfo = response.data;
                    this.creditCards = this.clientInfo.cards.filter(card => card.type == "CREDIT");
                    this.debitCards = this.clientInfo.cards.filter(card => card.type == "DEBIT" );
                    this.cardsTrueDebit = this.debitCards.filter(card => card.current == true);
                    this.cardsTrueCredit = this.creditCards.filter(card => card.current == true);
                   console.log(this.creditCards)
//                    console.log(this.debitCards)
//                    console.log(this.cardsTrueDebit)
                    console.log(this.cardsTrueCredit)
                })
                .catch((error) => {
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        formatDate: function (date) {
            return new Date(date).toLocaleDateString('en-gb');
        },
         disable: function (id) {
                   axios.patch(`/api/clients/current/cards/modify/`+id)
                                   .then(response => {
                                       return window.location.reload()
                                   })
                },
        signOut: function () {
            axios.post('/api/logout')
                .then(response => window.location.href = "/web/index.html")
                .catch(() => {
                    this.errorMsg = "Sign out failed"
                    this.errorToats.show();
                })
        },
    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.getData();
    }
}).mount('#app')