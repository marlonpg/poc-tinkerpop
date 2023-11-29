# poc-tinkerpop


Apache TinkerPop™ is an open source Graph Computing Framework. Within itself, TinkerPop represents a large collection of capabilities and technologies and, in its wider ecosystem, an additionally extended world of third-party contributed graph libraries and systems.

Gremlin helps you navigate the vertices and edges of a graph. He is essentially your query language to graph databases, as SQL is the query language to relational databases.

A graph is a collection of vertices (i.e., nodes, dots) and edges (i.e., relationships, lines), where a vertex is an entity which represents some domain object (e.g., a person or a place) and an edge represents the relationship between two vertices.


Why TinkerPop?
provider integrationThe goal of TinkerPop, as a Graph Computing Framework, is to make it easy for developers to create graph applications by providing APIs and tools that simplify their endeavors. One of the fundamental aspects to what TinkerPop offers in this area lies in the fact that TinkerPop is an abstraction layer over different graph databases and different graph processors. As an abstraction layer, TinkerPop provides a way to avoid vendor lock-in to a specific database or processor. This capability provides immense value to developers who are thus afforded options in their architecture and development because:

They can try different implementations using the same code to decide which is best for their environment.

They can grow into a particular implementation if they so desire; e.g., start with a graph that is designed to scale within a single machine and then later switch to a graph that is designed to scale horizontally.

They can feel more confident in graph technology choices, as advances in the state of different provider implementations are behind TinkerPop APIs, which open the possibility to switch providers with limited impact.

TinkerPop has always had the vision of being an abstraction over different graph databases. That much is not new and dates back to TinkerPop 1.x. It is in TinkerPop 3.x, however, that we see the introduction of the notion that TinkerPop is also an abstraction over different graph processors like Spark. The scope of this tutorial does not permit it to delve into "graph processors", but the short story is that the same Gremlin statement we wrote in the examples above can be executed to run in distributed fashion over Spark or Hadoop. The changes required to the code to do this are not in the traversal itself, but in the definition of the TraversalSource. You can again see why we encourage graph operations to be executed through that class as opposed to just using Graph. You can read more about these features in this section on hadoop-gremlin.




TODO:
Look how index creating works (documentation)

//Using a example graph provided by TinkerPop
gremlin> graph = TinkerFactory.createModern()
==>tinkergraph[vertices:6 edges:6]
gremlin> g = traversal().withEmbedded(graph)
==>graphtraversalsource[tinkergraph[vertices:6 edges:6], standard]
gremlin> g.V()
==>v[1]
==>v[2]
==>v[3]
==>v[4]
==>v[5]
==>v[6]
gremlin> g.V(1).values('name') //// (3)
==>marko

//Creating a graph and Vertices and Edges
gremlin> graph = TinkerGraph.open()
==>tinkergraph[vertices:0 edges:0]
gremlin> g = traversal().withEmbedded(graph)
==>graphtraversalsource[tinkergraph[vertices:0 edges:0], standard]
gremlin> v1 = g.addV("person").property(T.id, 1).property("name", "marko").property("age", 29).next()
==>v[1]
gremlin> v2 = g.addV("software").property(T.id, 3).property("name", "lop").property("lang", "java").next()
==>v[3]
gremlin> g.addE("created").from(v1).to(v2).property(T.id, 9).property("weight", 0.4)
==>e[9][1-created->3]


//to find a vertex
gremlin> g.V().has('name','marko')
==>v[1]
//to find a vertex considering the label (person)
gremlin> g.V().has('person','name','marko')
==>v[1]

//to find the vertex associated with "marko" by the "created" edge
gremlin> g.V().has("name", "marko").outE("created")
==>e[9][1-created->3]
//or this
gremlin> g.V().has("name", "marko").outE("created").inV()
==>v[3]
//or this
gremlin> g.V().has("name", "marko").out("created")
==>v[3]

//more than one match
gremlin> g.V().has('person','name',within('vadas','marko')).values('age')
==>29
==>27

//label as exclude to filter some vertex out
gremlin> g.V().has('person','name','marko').as('exclude').
           out('created').in('created').
           where(neq('exclude')).
           values('name')
==>josh
==>peter


//group by label
gremlin> g.V().group().by(label).by('name')
==>[software:[lop,ripple],person:[marko,vadas,josh,peter]]


graph = TinkerGraph.open()
graph.createIndex('userId', Vertex.class) //1

g = traversal().withEmbedded(graph)

getOrCreate = { id ->
  g.V().has('user','userId', id).
    fold().
    coalesce(unfold(),
             addV('user').property('userId', id)).next()  //2
}

new File('D:\home\github\poc-tinkerpop\wiki-Vote.txt').eachLine {
  if (!it.startsWith("#")){
    (fromVertex, toVertex) = it.split('\t').collect(getOrCreate) //3
    g.addE('votesFor').from(fromVertex).to(toVertex).iterate()
  }
}


getOrCreate = { id -> g.V().has('user','userId', id).fold().coalesce(unfold(),addV('user').property('userId', id)).next()}

new File('D:\home\github\poc-tinkerpop\wiki-Vote.txt').eachLine {
  if (!it.startsWith("#")){
    (fromVertex, toVertex) = it.split('\t').collect(getOrCreate) //3
    g.addE('votesFor').from(fromVertex).to(toVertex).iterate()
  }
}