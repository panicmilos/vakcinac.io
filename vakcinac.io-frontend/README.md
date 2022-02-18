# vakcinac.io-frontend
Projekat za UI projekta za predmet XML i Web Servisi.

## Stvari potrebne za pokretanje projekta

1. Dobra volja i neosuđujuć karakter
2. `Node.js v12+`
3. `NPM v6+`

## Koraci za pokretanje projekta pomoću `npm` CLI:
1. Preuzeti zavisnosti `npm i`
2. Definisati URL ka zadnjoj strani u `src/cfg.js` datoteci
```js
// Sluzbenik Servis
const CITIZEN_SERVANT_API_URL = 'http://localhost:8881';
// Gradjanin Servis
const CITIZEN_API_URL = 'http://localhost:8880';
```
3. Definisati aplikaciju koja se pokreće
```js
// Sluzbenik
export const app = 'CitizenServant';
// Gradjanin
export const app = 'Citizen';
```
4. Pokrenuti
```bash
npm run serve
```

<h1>Korisni linkovi za radno okruženje i biblioteka iz trećih žurki</h1>

| Lib | URL |
| :--- | :--- |
| <b>Node.js | https://nodejs.org/en/ |
| <b>NPM | https://www.npmjs.com/ |
| <b>Vue.js | https://vuejs.org/ |
| <b>JSON Forms | https://jsonforms.io/ |
| <b>Vuetify | https://vuetifyjs.com/en/ |