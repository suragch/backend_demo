import 'package:dart_server/controller.dart';
import 'dart_server.dart';

// This class sets up the controller that will handle our HTTP requests
class DartServerChannel extends ApplicationChannel {

  @override
  Future prepare() async {
    // auto generated code
    logger.onRecord.listen((rec) => print("$rec ${rec.error ?? ""} ${rec.stackTrace ?? ""}"));
  }

  @override
  Controller get entryPoint {
    final router = Router();

    // We are only setting up one route. 
    // We could add more below if we had them.
    // A route refers to the path portion of the URL.
    router
      .route('/[:id]') // this means the root path with an optional id variable
      .link(() => MyController()); // requests will be forwarded to our custom controller

    return router;
  }
}

