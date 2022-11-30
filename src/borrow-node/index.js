const http = require('http');
const express = require('express');
const bodyParser = require('body-parser');
const ecstatic = require('ecstatic');
const {proxy} = require('./lib/net');
const history = require('connect-history-api-fallback');
const {errorSysInit} = require('./lib/error');

// const port = 8889;
const port = 8201;

errorSysInit(process);


const app = express();

app.all('*', function (req, res, next){
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Methods', 'POST,GET');
    next();
});

app.use( bodyParser.urlencoded({extended: true}) )
app.use(bodyParser.json())
app.use(history());
app.use(ecstatic({ root: __dirname + '/dist' }));

app.post('/serve/proxy',async function(req,res){
    const {url,params,config,method} = req.body;
    console.log(url,params,config,method)
    proxy(url,params,config,method).then(v=>{
        res.status(200).json(v.data);
    }).catch(err=>{
        res.status(404);
    })
});

app.post('/verify',async function(req,res){
    res.status(200);
});

app.post('/login',async function(req,res){
    res.status(200);
});

app.post('/regist',async function(req,res){
    res.status(200);
});

app.post('/query',async function(req,res){
    res.status(200);
});

app.post('/login/do_login',async function(req,res){
    res.status(200).json(req.body);
});

app.post('/login/register',async function(req,res){
    res.status(200).json(req.body);
});


http.createServer(app).listen(port,function () {
    console.log(`应用实例，访问地址为 http://localhost:${port}`);
});

