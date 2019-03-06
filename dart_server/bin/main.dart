import 'package:dart_server/dart_server.dart';

Future main() async {
  final app = Application<DartServerChannel>()
      ..options.configurationFilePath = "config.yaml"
      ..options.port = 3000; // default is 8888

  //final count = Platform.numberOfProcessors ~/ 2;
  //await app.start(numberOfInstances: count > 0 ? count : 1);
  
  // use the commented lines above for production code
  // this is just to avoid bugs in our mock database
  await app.start(numberOfInstances: 1);  

  print("Application started on port: ${app.options.port}.");
  print("Use Ctrl-C (SIGINT) to stop running the application.");
}

// When starting the Aqueduct server using `aqueduct serve` this
// file is ignored. See https://stackoverflow.com/q/54964706/3681880
// 
// To use this file start the server using `dart bin/main.dart`
