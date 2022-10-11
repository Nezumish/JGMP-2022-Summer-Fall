var PROTO_PATH = __dirname + '/messaging.proto';

var parseArgs = require('minimist');
var grpc = require('@grpc/grpc-js');
var protoLoader = require('@grpc/proto-loader');
var packageDefinition = protoLoader.loadSync(
    PROTO_PATH,
    {keepCase: true,
     longs: String,
     enums: String,
     defaults: true,
     oneofs: true
    });
var messaging_proto = grpc.loadPackageDefinition(packageDefinition);

function main() {
  var argv = parseArgs(process.argv.slice(2), {
    string: 'target'
  });

  var target;
  if (argv.target) {
    target = argv.target;
  } else {
    target = 'localhost:9099';
  }
  var client = new messaging_proto.MessagingService(target,grpc.credentials.createInsecure());


  client.sendMessage({
    Type: 'PIGEON', 
    message: 'Hello from NodeJS family!', 
    sender: 'NODE_JS_CLIENT', 
    receiver: 'GOLANG_SERVER'
  }, function(err, response) {
    console.log(response);
    console.log('Я упал: ', err)
  });
}

main();