fare richieste http per chiedere i dati e mandarli se qualcuno li cambia dalla dashboard

type -> "SET | GET"
if type.value == SET
            set -> "[[ON | OFF]] / [[0-100]]"

if type.value == GET
            get -> "
{
  data_1: {
          ora_1: {
                  luci: xx,
                  tapp: yy
          },
          ...
  },
...
}