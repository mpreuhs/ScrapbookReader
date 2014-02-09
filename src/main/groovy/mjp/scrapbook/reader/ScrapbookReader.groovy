package mjp.scrapbook.reader

import com.hp.hpl.jena.rdf.model.Model
import com.hp.hpl.jena.rdf.model.ModelFactory
import com.hp.hpl.jena.rdf.model.Resource
import com.hp.hpl.jena.vocabulary.*

class ScrapbookReader {
    static void main(final args) {
        new ScrapbookReader().testWriteRdf()
    }
    
    void testReadScrapbook() {
        Model model = ModelFactory.createDefaultModel()
        
        InputStream inputstream = new FileInputStream('html/scrapbook.rdf')
        
        model.read(inputstream, " ")
        
        def iter = model.listStatements()
        
        while (iter.hasNext()) {
            def stmt = iter.nextStatement()     // get next statement
            def subject = stmt.getSubject()     // get the subject
            def predicate = stmt.getPredicate() // get the predicate
            def object = stmt.getObject()       // get the object
            
            print subject.toString()
            print ' ' + predicate.toString() + ' '
            
            if (object instanceof Resource) {
                print object.toString()
            } else {
                print ' "' + object.toString() + '"'
            }
            
            println ' '
        }
        
//        model.write(System.out)
    }
    
    void testWriteRdf() {
        String personURI    = "http://somewhere/JohnSmith"
        String givenName    = "John"
        String familyName   = "Smith"
        String fullName     = givenName + " " + familyName

        // create an empty Model
        Model model = ModelFactory.createDefaultModel()

        // create the resource
        // and add the properties cascading style\\\
        Resource johnSmith = model.createResource(personURI)
            .addProperty(VCARD.FN, fullName)
            .addProperty(VCARD.N,
                model.createResource()
                   .addProperty(VCARD.Given, givenName)
                   .addProperty(VCARD.Family, familyName))

        model.write(System.out)
    }
}
