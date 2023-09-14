Vue.createApp({

     data() {
            return {
              name: "",
              payment: 0,
              payments: [],
              bandera: 0,
             // item: "",
              errorToats: null,
              errorMsg: null,
              maxAmount: 0,
              fees: [],
          }
          },
          methods:{
              apply: function(event){
                  event.preventDefault();
                  axios.post("/api/loansType",{name: this.name, maxAmount: this.maxAmount, payments: this.payments})
                  .then(response => {
                    window.location.reload();

                  })
                  .catch((error) =>{
                      this.errorMsg = error.response.data;
                      this.errorToats.show();
                  })
              },
              addPayment: function(){

                console.log(this.bandera)
            //    console.log(this.item)
              for (i = 0; i < this.payments.length; i++) {
                if(this.payments[i]==this.payment){
                 console.log(this.payments[i])
                                this.bandera = 1;
                } else
                               {
                               this.bandera = 0;
                               }

              }

              if(this.bandera!=1){
               this.bandera = 0;
               this.payments.push(this.payment);
              }

              },
              deletePayment: function(){
                              this.payments.pop();
                            },
              signOut: function(){
                  axios.post('/api/logout')
                  .then(response => window.location.href="/web/index.html")
                  .catch(() =>{
                      this.errorMsg = "Sign out failed"
                      this.errorToats.show();
                  })
                  },

             finish: function(){
                  window.location.reload();

              }},

          mounted: function(){
              this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
              this.modal = new bootstrap.Modal(document.getElementById('confirModal'));
              this.okmodal = new bootstrap.Modal(document.getElementById('okModal'));


          }


      }).mount('#app')