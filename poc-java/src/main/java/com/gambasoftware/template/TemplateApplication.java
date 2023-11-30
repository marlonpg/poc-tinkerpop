package com.gambasoftware.template;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

public class TemplateApplication {

	public static void main(String[] args) {
		// Creating an in memory graph database (TinkerGraph)
		Graph graph = TinkerGraph.open();
		GraphTraversalSource g = graph.traversal();

		// Adding person vertices
		//next(), is a terminal step, which essentially ends the graph traversal and returns a concrete object
		Vertex v1 = g.addV("person").property("name", "Marlon").property("age", 33).next();
		// Adding pet vertices
		Vertex v2 = g.addV("cat").property("name", "Kira").property("age", 1).next();
		Vertex v3 = g.addV("cat").property("name", "Nami").property("age", 2).next();
		Vertex v4 = g.addV("cat").property("name", "Jatoba").property("age", 5).next();
		Vertex v5 = g.addV("cat").property("name", "Migi").property("age", 7).next();
		Vertex v6 = g.addV("dog").property("name", "Kog'Maw").property("age", 10).next();

		// Add edges
		g.V(v1).addE("has").to(v2).next();
		g.V(v1).addE("has").to(v3).next();
		g.V(v1).addE("has").to(v4).next();
		g.V(v1).addE("has").to(v5).next();
		g.V(v1).addE("has").to(v6).next();

		// Print vertices
		System.out.println("Vertices:");
		g.V().valueMap().forEachRemaining(System.out::println);

		// Print edges
		System.out.println("\nEdges:");
		g.E().valueMap().forEachRemaining(System.out::println);

		// Perform a simple traversal
		System.out.println("\nMarlon has these pets:");
		g.V(v1).out("has").values("name").forEachRemaining(System.out::println);

		//Count Pets the properties
		System.out.println(g.V().has("name","Marlon")
				.outE("has").count().next());

	}

}
