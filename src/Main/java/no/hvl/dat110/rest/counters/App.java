package no.hvl.dat110.rest.counters;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;

public class App {

	static Map<Long, Todo> todos = new HashMap<>();

	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8080);
		}

		after((req, res) -> res.type("application/json"));

		get("/todo", (req, res) -> new Gson().toJson(todos.values()));

		get("/todo/:id", (req, res) -> {
			Long id = Long.parseLong(req.params("id"));
			return todos.get(id).toJson();
		});

		post("/todo", (req, res) -> {
			Todo todo = new Gson().fromJson(req.body(), Todo.class);
			todos.put(todo.getId(), todo);
			return todo.toJson();
		});

		delete("/todo/:id", (req, res) -> {
			Long id = Long.parseLong(req.params("id"));
			return todos.remove(id).toJson();
		});

		put("/todo/:id", (req, res) -> {
			Long id = Long.parseLong(req.params("id"));
			Todo newTodo = new Gson().fromJson(req.body(), Todo.class);
			todos.replace(id, newTodo);
			return todos.get(id).toJson();
		});


	}
}