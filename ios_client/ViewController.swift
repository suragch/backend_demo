import UIKit

class ViewController: UIViewController {
    
    let host = "http://localhost:3000"

    // make GET (all) request
    @IBAction func makeGetAllRequestTapped(_ sender: UIButton) {
        
        guard let url  = URL(string: host) else {return}
        
        // background task to make request with URLSession
        let task = URLSession.shared.dataTask(with: url) { (data, response, error) in
            guard let statusCode = (response as? HTTPURLResponse)?.statusCode else {return}
            guard let body = data else {return}
            guard let jsonString = String(data: body, encoding: String.Encoding.utf8) else {return}
            
            print("GET result: \(statusCode) \(jsonString)")
        }
        
        // start the task
        task.resume()
    }
    
    // make GET (one) request
    @IBAction func makeGetOneRequestTapped(_ sender: UIButton) {
        
        let idToGet = 0;
        let urlString = "\(host)/\(idToGet)"
        guard let url  = URL(string: urlString) else {return}
        
        let task = URLSession.shared.dataTask(with: url) { (data, response, error) in
            guard let statusCode = (response as? HTTPURLResponse)?.statusCode else {return}
            guard let body = data else {return}
            guard let jsonString = String(data: body, encoding: String.Encoding.utf8) else {return}
            
            print("GET result: \(statusCode) \(jsonString)")
        }
        task.resume()
    }
    
    // make POST request
    @IBAction func makePostRequestTapped(_ sender: UIButton) {
        
        let dictionary = ["fruit" : "pear", "color" : "green"]
        
        // prepare request
        guard let url  = URL(string: host) else {return}
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        guard let json = try? JSONSerialization.data(withJSONObject: dictionary, options: []) else {return}
        request.httpBody = json
        
        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            guard let statusCode = (response as? HTTPURLResponse)?.statusCode else {return}
            guard let body = data else {return}
            guard let responseString = String(data: body, encoding: .utf8) else {return}
            print("POST result: \(statusCode) \(responseString)")
            
            // If your API returns JSON you could do the following:
            // guard let jsonString = try? JSONSerialization.jsonObject(with: body, options: []) else {return}
        }
        task.resume()
    }
    
    // make PUT request
    @IBAction func makePutRequestTapped(_ sender: UIButton) {
        
        let dictionary = ["fruit" : "watermellon", "color" : "red and green"]
        
        let idToPut = 0;
        let urlString = "\(host)/\(idToPut)"
        
        // prepare request
        guard let url  = URL(string: urlString) else {return}
        var request = URLRequest(url: url)
        request.httpMethod = "PUT"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        guard let json = try? JSONSerialization.data(withJSONObject: dictionary, options: []) else {return}
        request.httpBody = json
        
        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            guard let statusCode = (response as? HTTPURLResponse)?.statusCode else {return}
            guard let body = data else {return}
            guard let responseString = String(data: body, encoding: .utf8) else {return}
            print("PUT result: \(statusCode) \(responseString)")
        }
        task.resume()
    }
    
    // make PATCH request
    @IBAction func makePatchRequestTapped(_ sender: UIButton) {
        
        let dictionary = ["color" : "green"]
        
        let idToPatch = 0;
        let urlString = "\(host)/\(idToPatch)"
        
        // prepare request
        guard let url  = URL(string: urlString) else {return}
        var request = URLRequest(url: url)
        request.httpMethod = "PATCH"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        guard let json = try? JSONSerialization.data(withJSONObject: dictionary, options: []) else {return}
        request.httpBody = json
        
        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            guard let statusCode = (response as? HTTPURLResponse)?.statusCode else {return}
            guard let body = data else {return}
            guard let responseString = String(data: body, encoding: .utf8) else {return}
            print("PATCH result: \(statusCode) \(responseString)")
        }
        task.resume()
    }
    
    // make DELETE request
    @IBAction func makeDeleteRequestTapped(_ sender: UIButton) {
        
        let idToDelete = 0;
        let urlString = "\(host)/\(idToDelete)"
        
        // prepare request
        guard let url  = URL(string: urlString) else {return}
        var request = URLRequest(url: url)
        request.httpMethod = "DELETE"
        
        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            guard let statusCode = (response as? HTTPURLResponse)?.statusCode else {return}
            guard let body = data else {return}
            guard let responseString = String(data: body, encoding: .utf8) else {return}
            print("DELETE result: \(statusCode) \(responseString)")
        }
        task.resume()
    }
}

