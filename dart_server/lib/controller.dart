import 'dart:async';
import 'dart:io';
import 'package:aqueduct/aqueduct.dart';

// using a list to simulate a database for demonstration purposes
List<Map<String, dynamic>> mockDatabase = [
    {
        'fruit': 'apple',
        'color': 'red'
    },
    {
        'fruit': 'banana',
        'color': 'yellow'
    }
];

class MyController extends ResourceController {

  MyController() {
    acceptedContentTypes = [ContentType.json, ContentType.text];
  }

  // Handle GET (all) request
  @Operation.get()
  Future<Response> getAllFruit() async {
    // return the whole list
    return Response.ok(mockDatabase);
  }

  // Handle GET (one) request
  @Operation.get('id')
  Future<Response> getFruitByID(@Bind.path('id') int id) async {
    // error checking
    if (id < 0 || id >= mockDatabase.length){
      return Response.notFound(body: 'Item not found');
    }
    // return json for item at id
    return Response.ok(mockDatabase[id]);
  }

  // Handle POST request
  @Operation.post()
  Future<Response> addFruit() async {
    // get json from request
    final Map<String, dynamic> item = await request.body.decode(); // TODO validate data
    
    // create item (TODO return error status code if there was a problem)
    mockDatabase.add(item);
    
    // report back to client
    final int id = mockDatabase.length - 1;
    return Response.ok('Item added with id $id');
  }

  // Handle PUT request
  @Operation.put('id')
  Future<Response> putContent(@Bind.path('id') int id) async {
    // error checking
    if (id < 0 || id >= mockDatabase.length){
      return Response.notFound(body: 'Item not found');
    }
    // get the updated item from the client
    final Map<String, dynamic> item = await request.body.decode(); // TODO validate data
    // make the update
    mockDatabase[id] = item;
    // report back to the client
    return Response.ok('Item replaced at id $id');
  }

  // Handle PATCH request 
  // (PATCH does not have its own @Operation method so 
  // the constructor parameter is used.)
  @Operation('PATCH', 'id')
  Future<Response> patchContent(@Bind.path('id') int id) async {
    // error checking
    if (id < 0 || id >= mockDatabase.length){
      return Response.notFound(body: 'Item not found');
    }
    // get the updated item from the client
    final Map<String, dynamic> item = await request.body.decode(); // TODO validate data
    // make the partial update
    mockDatabase[id]['color'] = item['color'];
    // report back to the client
    return Response.ok('Item updated at id $id');
  }

  // Handle DELETE request 
  @Operation.delete('id')
  Future<Response> deleteContent(@Bind.path('id') int id) async {
    // error checking
    if (id < 0 || id >= mockDatabase.length){
      return Response.notFound(body: 'Item not found');
    }
    // do the delete
    mockDatabase.removeAt(id);
    // report back to the client
    return Response.ok('Item deleted at id $id');
  }
}

// Supplemental model class
// For help converting JSON to objects in Dart see
// this post https://stackoverflow.com/a/54657953
class Fruit {

  Fruit(this.fruit, this.color);

  // named constructor
  Fruit.fromJson(Map<String, dynamic> json)
      : fruit = json['fruit'] as String,
        color = json['color'] as String;

  int id;
  String fruit;
  String color;

  // method
  Map<String, dynamic> toJson() {
    return {
      'fruit': fruit,
      'color': color,
    };
  }
}
