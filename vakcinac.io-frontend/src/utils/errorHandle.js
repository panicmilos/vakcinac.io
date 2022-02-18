import x from 'xml2js'

export function errorHandle(e) {
    x.parseString(e.response.data, { mergeAttrs: true, explicitArray: false }, (err, result) => {
      if (err) {
        throw err;
      }
      alert(result.error.message)
    });
}