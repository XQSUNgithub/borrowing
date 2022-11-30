const axios = require('axios');

function proxy(url,params={},config={},method="post"){
    const handle =  axios.create(config);
    if(method==="post"){
        return handle.post(url,params);
    }
    return handle.get(url,params);
}

module.exports = {
    axios,
    proxy
};