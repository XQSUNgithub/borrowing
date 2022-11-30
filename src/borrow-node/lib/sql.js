const mysql = require('mysql2');
const {sql_config} = require('../config');

let status = false;

const timeout = 1000*60*60*2;

function START(){
    handle = mysql.createConnection(sql_config);
    status = false;
    handle.connect((err) => {
        if (err) {
            status = false;
            console.log("连接失败");
        }else{
            status = true;
            console.log("连接成功");
        }
    });
}

function CLOSE(){
    status = false;
    handle.end();
}

function INSERT(table="",body={}){
    return new Promise((resolve,reject)=>{
        handle.query(`insert into ${table} set ?`,body,(err,result)=>{
            if(err){
                resolve();
            }else{
                resolve(result);
            }
        });
    });
}

function DELETE(table="",cnd="",args=[]){
    return new Promise((resolve,reject)=>{
        handle.query(`delete from ${table} ${cnd?`where ${cnd} `:""}`,args,(err,result)=>{
            if(err){
                resolve();
            }else{
                resolve(result);
            }
        });
    });
}

function QUERY(table="",key="*",cnd="",args=[]){
    return new Promise((resolve,reject)=>{
        handle.query(`select ${key} from ${table} ${cnd?`where ${cnd} `:""}`,args,(err,result)=>{
            if(err){
                resolve();
            }else{
                resolve(result);
            }
        });
    });
}

function GAIN(block=''){
    return new Promise((resolve,reject)=>{
        handle.query(block,(err,result)=>{
            if(err){
                resolve();
            }else{
                resolve(result);
            }
        });
    });
}

function UPDATE(table="",cmd="",cnd="",args=[]){
    return new Promise((resolve,reject)=>{
        handle.query(`update ${table} set ${cmd} ${cnd?`where ${cnd}`:``}`,args,(err,result)=>{
            if(err){
                resolve();
            }else{
                resolve(result);
            }
        });
    });
}

function PING(){
    return new Promise(resolve=>{
        handle.ping(err=>{
            if(err){console.log(err);resolve(err);}
            else{console.log("success")}
        })
    })
}


START();

setInterval(()=>{
    PING().then(()=>{
        START();
    });
},timeout);

module.exports = {
    handle,
    START,CLOSE,INSERT,DELETE,QUERY,GAIN,UPDATE,PING
};