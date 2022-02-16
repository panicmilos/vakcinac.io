const nodemailer = require("nodemailer");

class MailService {
  init() {
    this.transporter = nodemailer.createTransport({
      pool: true,
      host: "mail.isobarot.com",
      port: 587,
      secure: false,
      auth: {
        user: 'contact@vakcinac.io',
        pass: 'vakcinac.io',
      },
    });
  }

  async sendMail(mailInfo) {
    const info = await this.transporter.sendMail(mailInfo);
  
    console.log("Message sent: %s", info.messageId);
    console.log("Preview URL: %s", nodemailer.getTestMessageUrl(info));
  }
}

module.exports = {
  MailService
}
