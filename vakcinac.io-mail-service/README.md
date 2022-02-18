# vakcinac.io-mail-service
Projekat za slanje email-ova na projektu za predmet XML i Web Servisi.

## Stvari potrebne za pokretanje projekta

1. Dobra volja i neosuđujuć karakter
2. `Node.js v12+`
3. `NPM v6+`

## Koraci za pokretanje projekta pomoću `npm` CLI:
1. Preuzeti zavisnosti `npm i`
2. Podesiti parametre mail servera u `src/mailService.js` datoteci
```js
{
  host: "mail.isobarot.com",
  port: 587,
  secure: false,
  auth: {
    user: 'contact@vakcinac.io',
    pass: 'vakcinac.io',
  },
}
```
3. Pokrenuti
```bash
npm start
```

<h1>Korisni linkovi za radno okruženje i biblioteka iz trećih žurki</h1>

| Lib | URL |
| :--- | :--- |
| <b>Node.js | https://nodejs.org/en/ |
| <b>NPM | https://www.npmjs.com/ |
| <b>Nodemailer | https://nodemailer.com/about/ |
| <b>Express | https://expressjs.com/ |
| <b>xml2js | https://www.npmjs.com/package/xml2js |