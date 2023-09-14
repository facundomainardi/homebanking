Vue.createApp({

    data() {
        return {
            clientInfo: {},
            errorToats: null,
            errorMsg: null,
            accounts: [],
            accountsActive: [],
            typeAccounts: "none",
        }
    },
    methods: {
        getData: function () {
            axios.get("/api/clients/current")
                .then((response) => {
                    //get client ifo
                    this.clientInfo = response.data;
                    this.accounts = this.clientInfo.account;
                    this.accountsActive = this.accounts.filter(account => account.active == true)
                     console.log(this.accountsActive)
                })
                .catch((error) => {
                    // handle error
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        formatDate: function (date) {
            return new Date(date).toLocaleDateString('en-gb');
        },
        signOut: function () {
            axios.post('/api/logout')
                .then(response => window.location.href = "/web/index.html")
                .catch(() => {
                    this.errorMsg = "Sign out failed"
                    this.errorToats.show();
                })
        },
        disable: function (id) {
                           axios.patch(`/api/clients/current/accounts/modify/`+id)
                                           .then(response => {
                                               return window.location.reload()
                                           })
                        },
        create: function () {
        event.preventDefault();
                    if (this.typeAccounts == "none" ) {
                        this.errorMsg = "You must select a account type ";
                        this.errorToats.show();
                    } else {
                        let config = {
                            headers: {
                                'content-type': 'application/x-www-form-urlencoded'
                            }
                        }
            axios.post(`/api/clients/current/accounts?typeAccounts=${this.typeAccounts}`, config)
                .then(response => window.location.reload())
                .catch((error) => {
                    this.errorMsg = error.response.data;
                    this.errorToats.show();
                })
        }
    }},
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.getData();
    }
}).mount('#app')