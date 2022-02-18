const { MailService } = require("./mailService");
const express = require("express");
const xmlparser = require("express-xml-bodyparser");

async function main() {
  const mailService = new MailService();
  mailService.init();

  const app = express();
  app.use(express.urlencoded({ extended: true }));
  app.use(xmlparser({ trim: false, explicitArray: false }));
  const port = 3000;

  app.post("/", async (req, res) => {
    console.log(req.body);
    const atts = req.body.mail.attachments;
    await mailService.sendMail({
      from: "contact@vakcinac.io",
      ...req.body.mail,
      attachments: Array.isArray(atts)
        ? atts
        : (atts ? [{...atts, contentType: 'blob'}] : undefined),
    });
    res.contentType("application/json").status(200).send({ message: "Ok" });
  });

  app.listen(port, () => {
    console.log(`vakcinac.io mail service listening on port ${port}`);
  });
}

main().catch(console.error);
