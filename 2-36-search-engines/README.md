# Topic 2 (#36): Search Engines (Lucene, Elastic, Solr)

## Application Info
The simple app to try Solr: reads and parses `.epub` books into SolrBook model to save 
them into the Solr index. The SolrBook may be obtained by its id or found by searching query.

###API
Every URL starts from `/api/v1/books`

####GET
`/{bookId}` - get book from Solr by its id\
`/suggest?query={}` - imitation of "Google like" search suggestions (just to try Solr Suggester)\
`with SearchBookRequestDto body` - filtered (equals given Value of the Field)/faceted/fulltext (content of the book or the Field contains tokens of the given Query) search request

####POST
Reads `.epub` books from the given folder in application.yaml

####DELETE
Clears the Solr core

## How to start
####Launch Solr via Docker:
`docker run -d -p 8983:8983 -v <your volume path> --name solr solr solr-precreate solr-book`

???

PROFIT.

####Autosuggestions configuration:
You need to edit `solrconfig.xml` somehow. I'm not smart enough to do it in a normal way, so here is the work around:\
copy Solr default config somewhere:\
`docker cp solr:/var/solr/ <somewhere>`

Find `solrconfig.xml` and add the following statements before the `</config>` tag:\
`<searchComponent name="suggest" class="solr.SuggestComponent">`\
`<lst name="suggester">`\
`<str name="name">default</str>`\
`<str name="lookupImpl">FuzzyLookupFactory</str>`\
`<str name="suggestAnalyzerFieldType">string</str>`\
`<str name="field">full_title</str>`\
`<str name="buildOnStartup">true</str>`\
`</lst>`

`</searchComponent>`\
`<requestHandler name="/suggest" class="solr.SearchHandler" startup="lazy">`\
`<lst name="defaults">`\
`<str name="suggest">true</str>`\
`<str name="suggest.count">3</str>`\
`</lst>`\
`<arr name="components">`\
`<str>suggest</str>`\
`</arr>`\
`</requestHandler>`

Then delete the initial `solrconfig.xml` from the docker volume\
Then copy the edited `solrconfig.xml` to the place of the deleted one:\
`docker cp <the_initial_copied_config_place/solrconfig.xml> solr:/var/solr/data/solr-book/conf/`

Then restart the container