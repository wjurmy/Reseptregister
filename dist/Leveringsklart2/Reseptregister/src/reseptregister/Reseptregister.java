/**
 * Mainklassen, leser registerobjekter fra fil, lagrer til fil og åpner login-vinduet
 */
package reseptregister;

import GUI.Login;
import Objekter.Lege;
import Objekter.Pasient;
import Objekter.Preparat;
import Objekter.Resept;
import Registerklasser.LagListe_PREPARAT;
import Registerklasser.Preparatregister;
import Registerklasser.Register;
import Registerklasser.Reseptmap;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

/*
 * @author Benjamin Aarstad Nygård
 * Sist endret:12.05.14
 */
public class Reseptregister {

    private static LinkedList<Preparat> prepliste;
    private static Register register;
    private static Reseptmap reseptregister;
    private static Preparatregister preparatregister;
    
    /**
     * Main metode som kaller på login-vinduet
     * @param args tom string? vi vet egentlig ikke..
     */
    public static void main(String[] args){

        prepliste = new LagListe_PREPARAT().getListe();
        lastRegister();
        lastReseptregister();
        preparatregister = new Preparatregister();
        /*
        //<editor-fold desc="Generer testverdier"> 
      
        //<editor-fold desc="Autogenerer testverdier">
        String[] fornavn = {"Vegard", "Hanne", "Stine", "hans", "Trovald", "Ole", "anne", "inger", "Kari", "Marit", "Ingrid", "Liv", "Eva", "Berit", "Astrid", "Bjørg", "Hilde", "Anna", "Solveig", "Marianne", "Randi", "Ida", "Nina", "Maria", "Elisabeth", "Kristin", "Bente", "Heidi", "Silje", "Gerd", "Linda", "Tone", "Tove", "Elin", "Anita", "Wenche", "Ragnhild", "Camilla", "Ellen", "Karin", "Hege", "Ann", "Else", "Mona", "Marie", "Aud", "Monica", "Julie", "Kristine", "Turid", "Laila", "Reidun", "Helene", "Åse", "Jorunn", "Sissel", "Mari", "Line", "Lene", "Mette", "Grethe", "Trine", "Unni", "Malin", "Grete", "Thea", "Gunn", "Emma", "May", "Ruth", "Lise", "Emilie", "Anette", "Kirsten", "Sara", "Nora", "Linn", "Eli", "Siri", "Cecilie", "Irene", "Marte", "Gro", "Britt", "Ingeborg", "Kjersti", "Janne", "Siv", "Sigrid", "Karoline", "Karen", "Vilde", "Martine", "Tonje", "Andrea", "Sofie", "Torill", "Synnøve", "Rita", "Jenny", "Cathrine", "Elise", "Maren", "Hanna", "Lillian", "Lena", "Brit", "Vigdis", "Therese", "Frida", "Amalie", "Ingvild", "Ingunn", "Bodil", "Charlotte", "Signe", "Lisbeth", "Sandra", "Christine", "Victoria", "Marthe", "Caroline", "Mia", "Tina", "Merete", "Oda", "Trude", "Vibeke", "Henriette", "Johanna", "Lisa", "Gunvor", "Katrine", "Mary", "Torunn", "Kirsti", "Beate", "Marita", "Christina", "Sonja", "Hedda", "Susanne", "Tuva", "Aslaug", "Gry", "Kristina", "Aase", "Toril", "Renate", "Kine", "Guro", "Maja", "Helga", "Mathilde", "Ane", "Aina", "Jeanette", "Sunniva", "Ingebjørg", "Eline", "Solfrid", "Rigmor", "Margit", "Gunhild", "Veronica", "Sølvi", "Ella", "Elsa", "Linnea", "Synne", "Birgit", "Kaja", "Anja", "Pernille", "Monika", "Gudrun", "Olaug", "Agnes", "Mina", "Aurora", "Magnhild", "Sarah", "Mariann", "Jorun", "Åshild", "Anne-Lise", "Pia", "Lill", "Kathrine", "Ina", "Julia", "Selma", "Celine", "Olga", "Margrethe", "Inga", "Hannah", "Karina", "Iselin", "Amanda", "Sigrun", "Miriam", "Erna", "Helen", "Torhild", "Benedicte", "Marta", "Birgitte", "Lilly", "Edith", "Evy", "Eirin", "Rikke", "Anniken", "Borghild", "June", "Karianne", "Tiril", "Martha", "Helle", "Carina", "Malene", "Svanhild", "Lina", "Klara", "Merethe", "Edel", "Annette", "Jane", "Anny", "Guri", "Sidsel", "Tordis", "Målfrid", "Leah", "Ellinor", "Hjørdis", "Tine", "Kamilla", "Rebecca", "Ester", "Live", "Kjellaug", "Astri", "Vera", "Siw", "Evelyn", "Maiken", "Dina", "Michelle", "Brita", "Jannicke", "Ine", "Alexandra", "Rebekka", "Oddny", "Dagny", "Torild", "Marion", "Eldbjørg", "Ann-Kristin", "Ada", "Hildegunn", "Madeleine", "Sofia", "Helena", "Greta", "Tora", "Judith", "Yvonne", "Elena", "Natalie", "Mathea", "Nathalie", "Ronja", "Oddbjørg", "Oddrun", "Vivian", "Frøydis", "Gina", "Madelen", "Haldis", "Borgny", "Ragna", "Alma", "Celina", "Laura", "Andrine", "Henny", "Josefine", "Solbjørg", "Lotte", "Rakel", "Ranveig", "Arnhild", "Hildur", "Lea", "Louise", "Stina", "Mai", "Benedikte", "Veronika", "Asbjørg", "Jan", "Per", "Bjørn", "Kjell", "Lars", "Arne", "Knut", "Svein", "Odd", "Tor", "Geir", "Terje", "Thomas", "Morten", "John", "Erik", "Anders", "Rune", "Martin", "Andreas", "Trond", "Tore", "Harald", "Olav", "Gunnar", "Jon", "Rolf", "Leif", "Tom", "Stian", "Kristian", "Nils", "Øyvind", "Helge", "Espen", "Einar", "Marius", "Kåre", "Daniel", "Magnus", "Fredrik", "Christian", "Steinar", "Eirik", "Håkon", "Øystein", "Henrik", "Frode", "Arild", "Ivar", "Jørgen", "Kjetil", "Frank", "Stein", "Johan", "Sverre", "Magne", "Petter", "Dag", "Alf", "Egil", "Vidag", "Vidar", "Stig", "Karl", "Jonas", "Pål", "Kenneth", "Tommy", "Roger", "Ola", "Kristoffer", "Erling", "Håvard", "Thor", "Reidar", "Eivind", "Asbjørn", "Finn", "Jens", "Roy", "Alexander", "Kim", "Torbjørn", "Bjarne", "Roar", "Simen", "Arvid", "Jarle", "Johannes", "Robert", "Sondre", "Mathias", "Ove", "Trygve", "Sigurd", "Sindre", "Jostein", "joakim", "erlend", "jørn", "oddvar", "andre", "atle", "ronny", "mats", "emil", "ragnar", "inge", "henning", "aleksander", "lasse", "paul", "even", "kai", "sander", "markus", "adrian", "johnny", "bjørnar", "arve", "tobias", "åge", "øivind", "sebastian", "david", "sven", "torstein", "christoffer", "robin", "peder", "bård", "benjamin", "audun", "birger", "glenn", "ståle", "kurt", "simon", "peter", "roald", "sigmund", "steffen", "mads", "torgeir", "jakob", "michael", "arnt", "willy", "joachim", "ørjan", "carl", "runar", "bernt", "marcus", "harry", "tormod", "halvor", "dagfinn", "arnfinn", "raymond", "truls", "william", "magnar", "olaf", "richard", "jonny", "mohammad", "christer", "elias", "oddbjørn", "cato", "ruben", "ketil", "endre", "oskar", "ulf", "herman", "kevin", "henry", "georg", "christopher", "kent", "yngve", "bent", "nikolai", "otto", "ottar", "are", "aksel", "haakon", "patrick", "dan", "bjarte", "karsten", "åsmund", "vetle", "sigbjørn", "isak", "tomas", "ingar", "ali", "preben", "edvard", "asle", "amund", "mikael", "oliver", "sivert", "børge", "aage", "fred", "gaute", "sveinung", "ernst", "oddmund", "jacob", "thorbjørn", "bengt", "sten", "trym", "nicolai", "bendik", "arthur", "jarl", "torleif", "alfred", "hallvar", "halvard", "tony", "jim", "jesper", "mikkel", "viggo", "idar", "anton", "oscar", "ingvar", "ken", "leiv", "mohamed", "jonathan", "snorre", "vegar", "gustav", "thore", "victor", "remi", "philip", "kasper", "tord", "vebjørn", "kolbjørn", "eskil", "stefan", "iver", "joar", "gjermund", "edvin", "rasmus", "viktor", "dennis", "sigve", "freddy", "mohammed", "ahmed", "brage", "gudmund", "arnold", "svenn", "kjartan", "gisle", "chris", "hugo", "sjur", "filip", "niklas", "torfinn", "øistein", "odin", "thorleif", "aslak", "asgeir", "ivan", "gard", "arnstein", "julian", "gøran", "ronald", "jan-Erik", "børre", "agnar", "axel", "edgar", "svend", "ulrik", "ingvald", "yngvar", "didrik", "kristen", "kaare", "paal", "gabriel", "august", "hallgeir", "kyrre", "theodor", "jone", "karstein", "tim", "albert", "ragnvald", "ludvig", "erland", "klaus", "kay", "eilif", "mikal", "noah", "matias", "brede", "guttorm", "ådne", "ingolf", "konrad", "jack", "eric", "arnulf", "bernhard", "tarjei", "wilhelm", "leon", "oddgeir", "toralf", "norvald", "samuel", "rene", "hassan", "krister", "Werner", "Casper", "Hermann", "Omar", "Rolv", "Hjalmar", "Nicholas", "adam", "fritz", "walter", "hilmar", "sturla", "charles", "vemund", "fabian", "edmund", "muhammad", "torjus", "jahn", "heine", "brynjar", "felix", "lukas", "mustafa", "njål", "torgrim", "ibrahim", "niels", "torkel", "torleiv", "abdul", "nicolay", "walid", "malvin", "max", "tron", "villy", "brian", "morgan", "allan", "torger", "hermod", "leo", "carsten", "eldar", "jonatan", "gunvald", "tage", "thorstein", "claus", "aron", "jørund", "gerhard", "rudi", "jimmy", "james", "Lars-Erik", "Rikard", "Birk", "Søren", "Elling", "Håvar", "fridtjof", "osvald", "jann", "amir", "lennart", "ahmad", "josef", "ludvik", "patrik", "jose", "thorvald", "ørnulf", "stephen", "wiggo", "eigil", "rudolf", "abdi", "ingebrigt", "oddvin", "mattis", "sture", "stephan", "ben", "ansgar", "Frederik", "Ole-Martin", "Lucas", "alv", "eyvind", "mehmet", "steven", "sigvald", "sølve", "torvald", "ask", "nicolas", "nicklas", "eilert", "eskild", "normann", "baard", "said", "teodor", "alan", "esben", "andrew", "mark", "syver", "brynjulf", "Hans-Petter", "aasmund", "Bror", "haldor", "remy", "steve", "joacim", "mons", "hogne", "ingve", "torben", "ian", "eystein", "kaj", "halfdan", "holger", "alex", "torje", "edward", "Ole-Kristian", "adnan", "rino", "leander", "gjert", "Kim-Andre", "syed", "hasan", "Sigfred", "lauritz", "Osman", "Carlos", "Hallstein", "Jean", "ronnie", "asmund", "juan", "frits", "marco", "gudbrand", "luis", "ismail", "Svein-Erik", "Niclas", "Torkild", "Thanh", "julius", "tonny", "khalid", "torkil", "mattias", "narve", "flemming", "viljar", "Tom-Erik", "annar", "anthony", "mario", "Per-Arne", "Kennet", "olve", "scott", "frithjof", "tarald", "samir", "phillip", "Tor-Erik", "kato", "hussein", "severin", "evald", "gorm", "joseph", "jonn", "Bjørn-Erik", "Johann", "laurits", "abdirahman", "Sean", "skjalg", "Jomar", "abdullah", "bertil", "oddleif", "benny", "andres", "leonard", "antonio", "Martinus", "torolf", "thoralf", "ahmet", "bjørge", "theo", "fredrick", "levi", "borgar", "ferdinand", "lorentz", "george", "torodd", "arnljot", "abdullahi", "jardar", "thormod", "bastian", "minh", "sigvart", "hamza", "Ole-Christian", "Tor-Arne", "Odvar", "Anfinn", "adolf", "andrzej", "Ingmar", "Louis", "Mathis", "Ole-Petter", "Christen", "Hamid", "Marvin", "Olai", "Jarand", "Joel", "Halvdan", "Per-Erik", "bilal", "piotr", "vilhelm", "eddie", "edin", "knud", "gert", "oluf", "artur", "Ole-Jørgen", "Yusuf", "tønnes", "Krzysztof", "reinert", "ted", "nikolas", "noralf", "øyvin", "halvar", "vincent", "enok", "herbjørn", "linus", "sam", "widar", "jamal", "erich", "gunder", "joen", "gulbrand", "michel", "tosten", "poul", "ellev", "siur", "arent", "biørn", "haagen", "hendrich", "abraham", "johanes", "henrich", "isabel", "Anne-Marie", "unn", "iris", "petra", "natalia", "Inger-Lise", "Olivia", "Oddveig", "Mille", "Margrete", "alice", "margot", "ingjerd", "tanja", "annie", "lone", "torbjørg", "kate", "esther", "sylvi", "marina", "Katarzyna", "emily", "isabell", "sylvia", "alfhild", "viktoria", "rannveig", "gunnhild", "Agnieszka", "barbro", "nelly", "magdalena", "May-Britt", "isabella", "margareth", "aleksandra", "ylva", "oline", "gjertrud", "iren", "margaret", "mariell", "christin", "Fatima", "audhild", "marlene", "vivi", "nadia", "hedvig", "elizabeth", "siren", "annika", "anine", "diane", "matilde", "erle", "kaia", "sanna", "jorid", "runa", "mie", "maya", "gøril", "vanja", "alva", "annbjørg", "katarina", "amina", "nancy", "sigrunn", "angelica", "thi", "joanna", "elisabet", "karine", "paula", "ingvill", "rønnaug", "norunn", "asta", "ana", "malgorzata", "inrina", "emilia", "iben", "regine", "oddlaug", "connie", "marielle", "gerda", "lovise", "barbara", "jeanett", "lisbet", "isabelle", "jessica", "sophie", "melissa", "elna", "cornelia", "venke", "bergljot", "herdis", "susann", "Anne-Grethe", "adele", "Milla", "Signy", "Mildrid", "Ulla", "Elida", "Vanessa", "Åsta", "Dagmar", "Ewa", "Kristi", "Harriet", "Julianne", "Valborg", "Tomine", "Karolina", "terese", "arna", "stella", "sanne", "mali", "dagrun", "birthe", "kitty", "cecilia", "elina", "margunn", "maia", "brynhild", "susan", "katharina", "bettina", "Ann-Mari", "tilde", "Beathe", "Alina", "Liss", "Birte", "Jannike", "Carmen", "Torun", "Elisa", "Jennifer", "Mira", "Kjerstin", "Åsa", "Thale", "Ingun", "Åsne", "Gunnvor", "Eira", "Linea", "Mariam", "Idun", "Christel", "Brith", "Maryam", "Ulrikke", "Torgunn", "Svetlana", "Anne-Karin", "Amelia", "Una", "Inger-Johanne", "Nanna", "Halldis", "Aisha", "Veslemøy", "Anneli", "fanny", "hermine", "Zahra", "gretha", "eileen", "beata", "ågot", "synøve", "gitte", "tatiana", "annlaug", "janicke", "reidunn", "elvira", "elly", "alvhild", "Ann-Karin", "Lydia", "Rachel", "Fride", "Doris", "Erica", "Sina", "Agnete", "Pauline", "Renata", "Ingfrid", "Ann-Helen", "Aida", "Kristiane", "Embla", "Solvor", "Sophia", "Jill", "Kari-Anne", "Marian", "Patricia", "Maj", "Ebba", "Kjellrun", "Nicole", "Catharina", "Ingerid", "Vilma", "Tale", "Jolanta", "Anni", "Anett", "Kjellfrid", "Jolene", "Daniela", "Kaisa", "Eldrid", "Katrin", "elen", "silja", "Magda", "alida", "kerstin", "lilli", "Anne-Britt", "Claudia", "Alise", "Dorota", "Mirjam", "Marlen", "Rose", "Lykke", "Yasmin", "Sigfrid", "Paulina", "Katja", "Luna", "Nicoline", "Sol", "Simone", "Silvia", "Adriana", "Frøya", "Snefrid", "Sylwia", "samira", "catrine", "betty", "thora", "Anne-Grete", "Irmelin", "Elzbieta", "Ann-Christin", "Gudny", "Angela", "Vilja", "Elfrid", "Juliane", "Solvår", "Lin", "Anlaug", "Jofrid", "Dorthe", "Agnethe", "Ramona", "Gunnlaug", "Annelise", "Ingelin", "Christiane", "Stephanie", "Cassandra", "Magny", "Irma", "Edna", "Vibecke", "Iwona", "Edle", "Turi", "Kajsa", "Janet", "Valentina", "Ingri", "Maud", "Justyna", "Herborg", "Gyda", "Nikoline", "fatma", "teresa", "wenke", "filippa", "lotta", "tara", "emine", "Anne-Berit", "Aasta", "Gabriela", "åslaug", "Juni", "Anne-Mette", "Leila", "Fadumo", "Tea", "Leonora", "Gudveig", "Rut", "Katinka", "Jasmin", "Magna", "Solrun", "Idunn", "Anbjørg", "Regina", "iman", "eivor", "emmy", "bertha", "Anne-Mari", "Lilja", "Jelena", "Fredrikke", "Martina", "Clara", "Gun", "Rosa", "Bertine", "Marry", "Kathe", "Josephine", "Tatjana", "Amy", "Belinda", "Aagot", "Sonia", "Aashild", "gunnbjørg", "elinor", "magni", "ninni", "Trine-Lise", "Mari-Ann", "Bianca", "Tyra", "Suzanne", "Wiktoria", "Peggy", "Agata", "Izabela", "Aneta", "Ailin", "Sahra", "Cathrin", "Angelika", "Siril", "Møyfrid", "Salma", "Sabrina", "Wilma", "Jana", "Venche", "Rebecka", "Sabine", "Inger-Marie", "Gunda", "Thelma", "Naomi", "Elbjørg", "Vårin", "Dorthea", "Kamila", "Tamara", "Khadilja", "Liva", "Hulda", "Anne-Sofie", "cristina", "serine", "hennie", "gabriella", "beatrice", "alvilde", "mariel", "melina", "amal", "jennie", "hitler", "aylar", "pamela", "donald", "tarzan", "mullah", "son", "Sokrates", "Marve"};
        String[] mellomnavn = {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "Ese", "Werner", "hogne", "gamst", "loe", "møller", "Hansen", "klepp", "nordhagen", "nergård", "froholt", "nyberg", "nesset", "fandevik", "nymo", "heung", "sung", "bredal", "omdal", "opdal", "Prestegård", "pilskog", "parveen", "palm", "hadler", "ravndal", "reiersen", "reime", "resell", "Ribe", "Riis", "rinde", "risa", "rokne", "rosenvinge", "sandli", "sand", "salte", "sandve", "Schanche", "schei", "schjetne", "schjølberg", "schou", "schrøder", "selle", "sem", "semb", "sirevåg", "sirnes", "sjo", "Aarstad", "sjaastad", "skaar", "skei", "skinnes", "skjelvik", "skjelbred", "skjerping", "takle", "tande", "telle", "tenold", "tessem", "thon", "tjelle", "tidemann", "tjessem", "toft", "ueland", "undheim", "Keti", "utsi", "ulven", "undrum", "ulland", "ursin", "vaa", "vaage", "vabø", "vagle", "valde", "valla", "valle", "valvik", "valø", "vang", "velle", "venås", "veum", "vie", "vigdal", "wold", "wiik", "wang", "waage", "wahl", "wik", "wangen", "waaler", "winge", "woll", "worren", "wulff", "yri", "yttervik", "ytreland", "zahl", "åkre", "åsebø", "østby", "øye", "øen", "østli", "øksnes", "øwre", "øyan", "nes", "nesse", "ness", "nordbø", "norum", "nordahl", "nybø", "nylund", "nordås", "norberg", "nesje", "Ngo", "nydal", "nyhagen", "nesbø", "nævdal", "narum", "moen", "moe", "meyer", "melby", "mork", "mørk", "midtbø", "mæland", "myhre", "midtun", "myrvoll", "myller", "mjøs", "myhr", "minde", "molnes", "myre", "misje", "moum", "misund", "mittet", "malm", "minge", "molvær", "muren", "moholt", "meek", "mæle", "mehl", "meier", "mogen", "lie", "lund", "lian", "lervik", "lohne", "lange", "løland", "liland", "lothe", "lyng", "lundby", "lode", "langås", "leknes", "lunder", "lindahø", "lindahl", "løvli", "linde", "langli", "liseth", "kleppe", "kvamme", "kvam", "krogh", "kjær", "kvåle", "kroken", "kvalvik", "korsnes", "kjos", "kildal", "kolberg", "krohn", "kaland", "kolnes", "krog", "kjølstad", "kruse", "jørstad", "jama", "jordal", "jahren", "juvik", "jevne", "juul", "jæger", "jentoft", "juell", "jordan", "iden", "idsø", "ihle", "igland", "indahl", "ihlen", "istad", "idland", "holm", "hauge", "hoel", "holmen", "hoff", "hamre", "helle", "holen", "husby", "holte", "haugan", "holt", "hovde", "holst", "holter", "holthe", "hope", "huse", "haugerud", "hernes", "hvidsten", "holdstad", "hals", "holand", "holme", "holtet", "haukås", "hovda", "hellum", "hage", "holden", "haave", "høydal", "hauan", "håvik", "heier", "heggland", "horgen", "hætta", "høiby", "hestad", "hasle", "heldal", "haavik", "hopen", "hynne", "grande", "gran", "gjerde", "grønvold", "granli", "gilje", "grønli", "grønning", "giske", "granheim", "grøndahl", "granum", "gjelsvik", "granberg", "greiner", "gaarder", "grimsrud", "groven", "grinde", "grytten", "godø", "Gaup", "grini", "gustad", "grøtte", "grønstad", "grøtting", "guddal", "graff", "gjelstad", "gjendem", "gunnerud", "grongstad", "gill", "glesnes", "granly", "grue", "groth", "gjestvang", "grønningsæter", "fredheim", "finstad", "fosse", "fjeld", "foss", "fagerli", "furuseth", "falch", "fauske", "fjell", "furulund", "friis", "finne", "furu", "fure", "fagerland", "fylling", "fornes", "fossdal", "frøland", "flem", "fossli", "fiskum", "fallet", "fjærli", "flaa", "formo", "flatebø", "furunes", "farestveit", "forland", "eide", "engen", "enger", "ervik", "evjen", "eidem", "eng", "eik", "engang", "engan", "egge", "erstad", "eie", "eid", "eira", "edland", "engelstad", "egeberg", "eike", "espe", "ege", "engebakken", "ersland", "engstrøm", "eknes", "engevik", "eikås", "engdal", "ekerhovd", "egeli", "engum", "eidsvik", "elvebakk", "evju", "endal", "eltvik", "dahl", "dale", "dybvik", "dreyer", "dalland", "dalheim", "dimmen", "daae", "dirdal", "digernes", "dyrstad", "dalby", "drage", "digre", "devik", "devold", "djønne", "dybwad", "derås", "dragland", "djuve", "dalaker", "Celius", "carlson", "berg", "bergh", "bakke", "berge", "bøe", "brekke", "bjerke", "breivik", "bye", "borge", "bolstad", "bergli", "blindheim", "bang", "bugge", "brenden", "brenna", "bergum", "brun", "bjerknes", "blom", "braaten", "bjørke", "buer", "bralie", "borg", "beck", "birkelund", "blix", "bull", "bjordal", "bore", "bauge", "bru", "brox", "bryhn", "børve", "bolme", "bjerga", "berdal", "busch", "brunstad", "aas", "aune", "ask", "aakre", "alstad", "aarseth", "almås", "alvestad", "alme", "aarnes", "amdal", "angell", "aass", "aske", "aarvik", "apeland", "aasbø", "aardal", "alfsen", "Aker", "aarø", "aanes", "ausland", "asphaug", "aakvik", "aspaas", "aksdal", "askim", "astrup", "aunan", "anwar", "aaby", "aurstad", "aabel", "alsvik", "angvik", "aabø", "aaseth", "andvik", "aarflot", "aam", "amble", "aae", "alver", "aallum", "amdam", "aandahl"};

        String[] etternavn = {"Lokreim", "andersen", "andreassen", "amundsen",
            "andresen", "aas", "arnesen", "antonsen", "aasen", "aune", "abrahamsen", "ali",
            "ahmed", "aase", "abraham", "arntsen", "andersson", "arntzen", "aamodt", "aasheim",
            "aronsen", "ask", "austad", "askeland", "alstad", "aasland", "aslaksen", "almås",
            "akselsen", "adolfsen", "aksnes", "aarseth", "alvestad", "aakre", "asbjørnsen",
            "akhtar", "albertsen", "amdal", "albrigtsen", "aarnes", "abelsen", "alme", "aaserud",
            "angell", "anderson", "alnes", "apeland", "aspelund", "aasbø", "arvesen", "aasgaard",
            "aleksandersen", "aure", "alm", "aalberg", "asheim", "aarhus", "angelsen", "austrheim",
            "alfsen", "alfredsen", "axelsen", "aarø", "ausland", "aasebø", "aulie", "arneberg", "aaberg",
            "aanestad", "arnestad", "aker", "anfinsen", "aaslund", "askvik", "aagesen", "asp", "berg", "bakken",
            "bakke", "berntsen", "berge", "bråthen", "brekke", "bøe", "breivik", "berger", "borge", "børresen",
            "berget", "bjelland", "bolstad", "bergersen", "bredesen", "bjørnsen", "bugge", "bang", "bergan",
            "benjaminsen", "bertelsen", "barstad", "bekken", "bergh", "bergli", "brenden", "berntzen", "bruun",
            "blindheim", "brenna", "bårdsen", "brun", "begum", "bentzen", "bekkelund", "bjørke", "byberg", "brandal",
            "buer", "bratlie", "birkelund", "beck", "borg", "bergesen", "bøhn", "brandt", "bruland", "bertheussen", "berentsen", "bogen", "bjordal", "bull", "bech", "brynhildsen", "brovold", "blix", "brekken", "berland", "bratli", "butt", "bru", "berthelsen", "bjørgum", "christensen", "carlsen", "caspersen", "corneliussen", "Chen", "claussen", "carlsson", "christiansen", "cappelen", "celius", "carlson", "chaudhry", "clausen", "dahl", "danielsen", "dahle", "dale", "dalen", "davidsen", "fredriksen", "didriksen", "dybvik", "dokken", "drange", "dammen", "dahlberg", "dreyer", "dyrnes", "dybdahl", "dalland", "djupvik", "dørum", "dalheim", "dalby", "dimmen", "dahlstrøm", "daae", "ditlefsen", "dyb", "dirdal", "digernes", "dyrstad", "dang", "dyrdal", "drage", "dalene", "dyngeland", "dehli", "dæhli", "dæhlie", "drønen", "dagsland", "drabløs", "drivenes", "dalhaug", "duong", "digre", "dyrøy", "devik", "devold", "djønne", "daleng", "dyrkorn", "dragland", "dybwad", "derås", "dalseth", "drevland", "djuve", "diesen", "drageset", "dramstad", "eriksen", "eide", "evensen", "edvardsen", "ellingsen", "eliassen", "engen", "eilertsen", "eggen", "egeland", "enger", "eikeland", "engebretsen", "endresen", "erlandsen", "engh", "ervik", "enoksen", "evjen", "eng", "eidem", "eik", "engan", "eikrem", "erdal", "ekeberg", "espedal", "eie", "eid", "eira", "elvestad", "edland", "emilsen", "eek", "erga", "eiken", "egeberg", "eikenes", "erikstad", "eike", "eskeland", "engelsen", "espe", "engebakken", "ege", "ersland", "engstrøm", "eknes", "engevik", "engdal", "eggum", "eikås", "eckhoff", "espenes", "elmi", "engvik", "eidet", "eltvik", "ekeli", "evje", "elnes", "elden", "evenrud", "emanuelsen", "edvartsen", "evanger", "einvik", "engenes", "emberland", "englund", "foss", "fossum", "fjeld", "fosse", "finstad", "fjeldstad", "farstad", "frantzen", "fredheim", "frydenlund", "fossen", "fagerli", "førde", "flaten", "furnes", "falch", "furuseth", "fjellheim", "førland", "forsberg", "fagerheim", "fossheim", "fauske", "fjørtoft", "fjell", "folkestad", "furulund", "frøyland", "fuglestad", "fjelde", "fremstad", "forberg", "fagerland", "fure", "fevang", "furu", "finne", "flåten", "fotland", "finsrud", "frogner", "friis", "fornes", "frafjord", "friestad", "falck", "frostad", "frøland", "flo", "fjelldal", "flem", "fossli", "follestad", "flatland", "ferkingstad", "forseth", "formo", "friberg", "flaa", "frøyen", "finseth", "frydenberg", "forsmo", "fiskvik", "fjellanger", "fjellvang", "fiskum", "feragen", "fallet", "fjærli", "framnes", "finnerud", "fylkesnes", "figenschou", "gundersen", "gulbrandsen", "gjerde", "gustavsen", "gabrielsen", "gran", "gjertsen", "grimstad", "gulliksen", "grande", "guttormsen", "grønvold", "gravdal", "gjerstad", "granli", "gregersen", "gilje", "grindheim", "grønli", "grønning", "gaustad", "giske", "granheim", "gjesdal", "granum", "grøndahl", "granlund", "gudmundsen", "gudbrandsen", "greiner", "green", "gaarder", "grinde", "groven", "gerhardsen", "gamst", "gaup", "gullaksen", "granås", "grini", "grytten", "garnes", "granerud", "gashi", "gudmestad", "grønlund", "grøtte", "gram", "goa", "garvik", "gustafsson", "grøtting", "graff", "gjelstad", "gjendem", "grongstad", "gilberg", "gunnerud", "gjessing", "grønningsæter", "granly", "grue", "gausdal", "gjerdrum", "groth", "grønbech", "gjersøe", "gjønnes", "grindhaug", "gjengedal", "grebstad", "giæver", "grønnestad", "guldberg", "Gjermundsen", "gjelsten", "granmo", "gleditsch", "gjerdingen", "grimsmo", "Gjerløw", "georgsen", "gulli", "grinden", "gillebo", "gjervik", "grønlien", "gule", "gjerustad", "hansen", "haugen", "hagen", "halvorsen", "henriksen", "holm", "hauge", "hanssen", "haugland", "haug", "helland", "hermansen", "helgesen", "hovland", "hoel", "hammer", "håland", "holmen", "hoff", "haga", "hamre", "haaland", "holen", "helle", "haugan", "hole", "husby", "hetland", "holte", "holt", "haraldsen", "holter", "hjelle", "håkonsen", "hovde", "holst", "hussain", "høyland", "hope", "holthe", "hassan", "hovden", "horn", "huseby", "heimdal", "huse", "haugerud", "handeland", "hoem", "hågensen", "hopland", "holten", "heggelund", "hove", "hofstad", "hillestad", "haldorsen", "hernes", "høvik", "haukås", "husebø", "herland", "hals", "høiland", "holme", "helmersen", "holtan", "hellerud", "heggen", "hauger", "heggem", "hussein", "høie", "hov", "hansson", "huynh", "haukeland", "hellum", "hogstad", "hjelmeland", "heiberg", "iversen", "isaksen", "ingebrigtsen", "ibrahim", "ingvaldsen", "iqbal", "ingebretsen", "ismail", "idland", "irgens", "isachsen", "indrebø", "igland", "ims", "istad", "ihle", "idsøe", "indergård", "isdahl", "iden", "indahl", "innvær", "isdal", "ihlen", "johansen", "jensen", "johnsen", "johannesen", "jacobsen", "jørgensen", "jakobsen", "jenssen", "jansen", "johannsesen", "jonassen", "johansson", "johnsrud", "jørstad", "josefsen", "juliussen", "johnson", "johnsson", "jahren", "jama", "jahr", "jansson", "jordal", "jahnsen", "jæger", "juul", "jessen", "jordet", "jones", "jønsson", "juvik", "jevne", "johanson", "jøssang", "joakimsen", "josdal", "joa", "juell", "jeppesen", "kristiansen", "karlsen", "knutsen", "kristoffersen", "kristensen", "knudsen", "kvam", "kolstad", "kvamme", "kleven", "kleppe", "krogstad", "klausen", "khan", "krogh", "kaspersen", "kleiven", "karlsson", "korneliussen", "kvalheim", "kaur", "kjær", "kaasa", "kvåle", "kvalvik", "kroken", "kopperud", "karlstad", "kvammen", "kittelsen", "karstensen", "kolberg", "kjærstad", "kildal", "korsmo", "kjos", "kallevik", "korsnes", "kjeldsen", "kirkeby", "karoliussen", "krohn", "kaland", "klungland", "kvinge", "kvarme", "klemetsen", "kielland", "klepp", "kilen", "kruse", "kråkenes", "kvaal", "kyllingstad", "krane", "krasniqi", "knapstad", "knoph", "killi", "klokk", "kinn", "klaussen", "klæboe", "Kolltveit", "kvande", "larsen", "lund", "lie", "lunde", "lien", "ludvigsen", "lorentzen", "løken", "løkken", "langeland", "lind", "lian", "larssen", "lauritzen", "lindberg", "lervik", "larsson", "lande", "lundberg", "lid", "løland", "lia", "langseth", "lyngstad", "lorentsen", "lohne", "lange", "løvås", "lystad", "liland", "lothe", "løvik", "lindseth", "lier", "lindstad", "lindgren", "løseth", "løberg", "lea", "løvdal", "linnerud", "leirvik", "lillebø", "lunden", "løvaas", "lyng", "lindland", "lindstrøm", "lundby", "loe", "lønning", "leknes", "lein", "løkke", "lima", "landro", "landsverk", "langås", "langnes", "landa", "lindahl", "lunder", "lied", "lægreid", "leite", "lygre", "leine", "langhelle", "lofthus", "lervåg", "lundemo", "linde", "lundgren", "løkås", "langli", "lysø", "moen", "martinsen", "mathisen", "moe", "myhre", "mikkelsen", "myklebust", "madsen", "mikalsen", "mortensen", "magnussen", "monsen", "møller", "Bigset", "markussen", "mæland", "myrvang", "meland", "meyer", "myrvold", "melby", "mathiesen", "mork", "mørk", "michelsen", "martinussen", "meling", "midtbø", "lippestad", "myhren", "matre", "mørch", "melhus", "moberg", "melbye", "myrland", "myhr", "midtun", "myrvoll", "myller", "moi", "myran", "mahmood", "mohammad", "mundal", "malmin", "minde", "molvik", "mjøen", "mauseth", "myrhaug", "myking", "misje", "mye", "Mittet", "muri", "Molland", "Moldestad", "Midtgård", "melheim", "Malm", "møgster", "moldskred", "mong", "mikkelborg", "meek", "melsom", "mandal", "mossige", "myrdal", "mikaelsen", "nilsen", "Nygård", "nielsen", "nguyen", "næss", "nilssen", "nygaard", "nordby", "nikolaisen", "nordli", "ness", "nesse", "norheim", "nordbø", "nyhus", "nyborg", "normann", "nordvik", "nordahl", "nilsson", "nes", "nymoen", "nicolaisen", "nyland", "nyheim", "nordal", "enebakk", "norum", "nordgård", "nesheim", "nylund", "nybø", "nakken", "nordnes", "netland", "nordberg", "nordseth", "nedrebø", "nystuen", "nervik", "nordstrøm", "min", "park", "Ngo", "nordhaug", "nysæther", "nordtveit", "nøkleby", "nysveen", "nordland", "nesland", "baasnes", "nyseth", "olsen", "ottesen", "olaussen", "olafsen", "opsahl", "opheim", "olstad", "olsson", "odden", "ovesen", "olufsen", "oftedal", "omdal", "omland", "olaisen", "oppedal", "opdal", "osmundsen", "ofstad", "ommundsen", "osnes", "osland", "otterlei", "opdahl", "osen", "opsal", "opedal", "osman", "omar", "opstad", "ottersen", "ottosen", "olsvik", "onarheim", "olavsen", "oma", "oen", "opland", "otterstad", "omholt", "olden", "otnes", "ose", "overå", "ommedal", "onstad", "oterhals", "oppegaard", "oliversen", "ophus", "obrestad", "oksnes", "moxnes", "ottem", "olberg", "orvik", "Nissen", "owren", "ylvisåker", "pedersen", "pettersen", "paulsen", "petersen", "pham", "persson", "persen", "petterson", "pettersson", "phan", "poulsen", "paulsrud", "prytz", "pollestad", "prestegård", "plassen", "palm", "presthus", "parveen", "pladsen", "paus", "pilskog", "polden", "paasche", "pleym", "pihl", "quisling", "vihovde", "raa", "raaen", "raastad", "rabben", "rafoss", "rahimi", "rahman", "raja", "raknes", "rakvåg", "ramberg", "ramfjord", "ramsdal", "ramsland", "ramstad", "ramsvik", "ramsøy", "rana", "randen", "runden", "ranheim", "ranum", "rasch", "rashid", "ravn", "raza", "ree", "reed", "refsdal", "refvik", "reime", "reinertsen", "reistad", "reitan", "reite", "reiten", "rekdal", "rekstad", "remmen", "reppe", "remøy", "repstad", "riise", "richardsen", "reyes", "Rikardsen", "rindal", "ringen", "Robstad", "Rist", "risnes", "risøy", "roberg", "rodal", "roen", "rognan", "rognes", "rognli", "roksvåg", "romsdal", "rongved", "rosenberg", "rosenlund", "rosenvinge", "rolland", "roos", "saastad", "saeed", "safi", "saga", "sagbakken", "sagen", "sagmo", "sagstad", "sagstuen", "sakariassen", "sakshaug", "salamonsen", "salberg", "salbu", "salih", "Salami", "salvesen", "samnøy", "Sand", "sandaker", "sandal", "sande", "sandersen", "sandhåland", "sandli", "sandland", "sandtorv", "schanke", "schei", "scheie", "schjeldrup", "schjerven", "schmidt", "schøyen", "seeberg", "seland", "sekse", "Seppola", "selmer", "sharif", "sara", "sevaldsen", "severinsen", "shala", "Silva", "sigurdsen", "sigvartsen", "simensen", "singh", "sjøberg", "sjøthun", "skagen", "skagestad", "skarbø", "skjelbred", "skjerven", "skjetne", "skjervold", "tafjord", "takvam", "taksdal", "tandberg", "talberg", "tang", "tandstad", "taraldsen", "taranger", "taule", "taylor", "teien", "teig", "teigen", "teigland", "tellefsen", "tennfjord", "tengesdal", "terjesen", "tesdal", "thommesen", "thompson", "theodorsen", "tharaldsen", "thoen", "nøkland", "hobbesland", "thon", "torbjørnsen", "thorgersen", "thorkildsen", "thorsrud", "thrane", "thu", "thomas", "tetlie", "Sippola", "thronæs", "thuen", "thunes", "thygesen", "tjelta", "tjøstheim", "tobiassen", "tofte", "Koti", "Syfte", "søvde", "ulriksen", "utne", "ulvestad", "urdal", "ulstein", "ugland", "urke", "utheim", "ulseth", "Tutu", "utvik", "ustad", "ulvund", "ulleberg", "uthus", "utgård", "ulvik", "ulset", "uthaug", "unhjem", "ullestad", "underhaug", "ugelstad", "vadseth", "vaksdal", "valberg", "valderhaug", "valen", "valland", "vallestad", "valseth", "valstad", "vang", "vangen", "vangsnes", "vanvik", "varhaug", "vartdal", "vassbotn", "vassdal", "vatland", "vatle", "vatn", "vatne", "vegsund", "vestad", "vestby", "vestli", "vestly", "vestnes", "vestrheim", "vestøl", "vevang", "viddal", "wathne", "westby", "wang", "wilhelmsen", "walle", "westgaard", "wergeland", "willumsen", "westbye", "wilson", "wessel", "wolden", "westad", "wiggen", "warsame", "williams", "walderhaug", "woll", "winsnes", "wallin", "wærnes", "walstad", "with", "wien", "wangberg", "wiklund", "wikstrøm", "westerlund", "wollan", "xavier", "Yusuf", "Yndestad", "Ytreland", "yttervik", "Yildiz", "Yilmaz", "Ytterstad", "Yri", "zachariassen", "zakariassen", "zhang", "zimmermann", "åsheim", "åsen", "årdal", "åsland", "ådland", "årvik", "årseth", "årnes", "ånensen", "åmodt", "ågotnes", "ødegaard", "øien", "østby", "øverland", "økland", "øyen", "øvrebø", "øverby", "østensen", "østrem", "øygarden", "øen", "øren", "øverås", "østgård", "øverli", "øvergård", "øie", "østbye", "østvold", "østerhus", "østbø", "øygard", "østebø", "østhus", "østlie", "ødegarden", "øvretveit", "øglænd", "østerås", "øvergaard", "øvereng", "østerud", "øvstebø", "øiestad", "ølberg", "østenstad",
            "østlund", "nossum",
            "rimmen",
            "Fleksnes"};

        String dag[] = new String[28];
        String mnd[] = new String[12];
        String år[] = new String[50];

        for (int i = 0;
                i < dag.length;
                i++) {
            dag[i] = String.valueOf(i + 1);
        }
        for (int i = 0; i < mnd.length; i++) {
            mnd[i] = String.valueOf(i + 1);
        }
        for (int i = 0;
                i < år.length;
                i++) {
            år[i] = String.valueOf(i + 45);
        }

        Random randomGenerator = new Random();

        for (int i = 0; i < 1000; i++) {
            int fornavnindex = randomGenerator.nextInt(fornavn.length);
            int mellomnavnindex = randomGenerator.nextInt(mellomnavn.length);
            int etternavnindex = randomGenerator.nextInt(etternavn.length);
            int dagindex = randomGenerator.nextInt(dag.length);
            int mndindex = randomGenerator.nextInt(mnd.length);
            int årindex = randomGenerator.nextInt(år.length);
            int siste = 10000 + randomGenerator.nextInt(80000);

            String dag1 = dag[dagindex];
            String mnd1 = mnd[mndindex];
            String år1 = år[årindex];

            if (Integer.parseInt(dag1) < 10) {
                dag1 = "0" + dag1;
            }
            if (Integer.parseInt(mnd1) < 10) {
                mnd1 = "0" + mnd1;
            }

            String fnr = dag1 + mnd1 + år[årindex] + siste;

            String fn = fornavn[fornavnindex];

            String fnSub1 = fn.substring(0, 1).toUpperCase();
            String fnSub2 = fn.substring(1, fn.length()).toLowerCase();

            fn = fnSub1 + fnSub2;

            String mn = mellomnavn[mellomnavnindex];

            if (mn == null) {
                mn = "";
            } else {
                String mnSub1 = mn.substring(0, 1).toUpperCase();
                String mnSub2 = mn.substring(1, mn.length()).toLowerCase();

                mn = mnSub1 + mnSub2;
            }

            String en = etternavn[etternavnindex];

            String enSub1 = en.substring(0, 1).toUpperCase();
            String enSub2 = en.substring(1, en.length()).toLowerCase();

            en = enSub1 + enSub2;

            register.leggTil(new Pasient(fnr, fn, mn, en));
            reseptregister.leggTilPerson(fnr);
            System.out.println(i + 1);

        }

        lagreRegister();

        String[] arbsteder = {"Haukeland Universitetssykehus", "Ullevål sykehus", "Rikshospitalet", "Storgata legekontor","SiO Nydalen" ,"SiO Sentrum", "Storoklinikken", "Førde sykehus", "Byremo legekontor", "Lærdal sykehus", "Aker sykehus", "Volvat Majorstuen", "Kristiansand sykehus", "Søgne legekontor"};

        for (int i = 0; i < 100; i++) {
            int fornavnindex = randomGenerator.nextInt(fornavn.length);
            int mellomnavnindex = randomGenerator.nextInt(mellomnavn.length);
            int etternavnindex = randomGenerator.nextInt(etternavn.length);
            int dagindex = randomGenerator.nextInt(dag.length);
            int mndindex = randomGenerator.nextInt(mnd.length);
            int årindex = randomGenerator.nextInt(år.length);
            int siste = 1 + randomGenerator.nextInt(10000);
            int arb = randomGenerator.nextInt(arbsteder.length);

            //String legenummer = register.genererLegenummer();
            String legenummer = String.valueOf(i + 1);

            String fn = fornavn[fornavnindex];

            String fnSub1 = fn.substring(0, 1).toUpperCase();
            String fnSub2 = fn.substring(1, fn.length()).toLowerCase();

            fn = fnSub1 + fnSub2;

            String mn = mellomnavn[mellomnavnindex];

            if (mn == null) {
                mn = "";
            } else {
                String mnSub1 = mn.substring(0, 1).toUpperCase();
                String mnSub2 = mn.substring(1, mn.length()).toLowerCase();

                mn = mnSub1 + mnSub2;
            }

            String en = etternavn[etternavnindex];

            String enSub1 = en.substring(0, 1).toUpperCase();
            String enSub2 = en.substring(1, en.length()).toLowerCase();

            en = enSub1 + enSub2;

            Lege l = new Lege(legenummer, fn, mn, en, "test");
            l.setArbeidssted(arbsteder[arb]);
            register.leggTil(l);
            reseptregister.leggTilPerson(l.getID());
            System.out.println("Lege " + i);

        }

        LinkedList<Pasient> pasientliste = (LinkedList<Pasient>) register.getPasientliste();
        LinkedList<Lege> legeliste = (LinkedList<Lege>) register.getLegeliste();

        prepliste = new LagListe_PREPARAT().getListe();

        for (int i = 0;
                i < pasientliste.size();
                i++) {
            Pasient pasient = pasientliste.get(i);

            Lege lege = (Lege) legeliste.get(randomGenerator.nextInt(legeliste.size()));
            lege.add(pasient);
            pasient.setFastlege(lege);
            System.out.println("Pasient fpr fastlege: " + i);
        }
//
//        //</editor-fold>
        
        //<editor-fold desc="Genererer resepter til testverdiene">
        
        
        Random r = new Random();
        
       
        String[] dager = new String[20];
        String [] måneder = new String[12];
        String[] årstall = {"2011","2012","2013","2014"};
        
        for(int i = 0; i < dager.length; i++){
            dager[i] = String.valueOf(i+1);
        }
        for(int i = 0; i < måneder.length; i++){
            if(i+1 > 10){
                måneder[i] = "0"+String.valueOf(i+1);
            }else{
                måneder[i] = String.valueOf(i+1);
                
            }
        }
        
        String[] førMai = {"01","02","03","04","05"};
        
        
        DateFormat datoformat = new SimpleDateFormat("dd-MM-yyyy");

        for (int i = 0;
                i < register.getPasientliste().size(); i++) {
            for (int h = 0; h < 10; h++) {
                Pasient pasient = (Pasient) register.getPasientliste().get(i);
                Lege lege = (Lege) register.getLegeliste().get(r.nextInt(register.getLegeliste().size()));

                Preparat preparat = prepliste.get(r.nextInt(prepliste.size()));

                
               
                
                
                String day = dager[r.nextInt(dager.length)];
                String year = årstall[r.nextInt(årstall.length)];
                
                String month;
                
                if(year.equalsIgnoreCase("2014")){
                    month = førMai[r.nextInt(førMai.length)];
                }else{
                    month = måneder[r.nextInt(måneder.length)];
                }
                
                String datoString = day +"-"+ month +"-"+ year;
                
                 Date dato;
                try {
                    dato = datoformat.parse(datoString);
                } catch (ParseException ex) {
                    dato = new Date();
                }
                
                
                
                Resept resept = new Resept(dato, pasient, lege, preparat, "15mg", "Tas 1-3 ganger pr dag etter avtale med lege!");
                

                reseptregister.addResept(pasient.getID(), resept);
                reseptregister.addResept(lege.getID(), resept);
                System.out.println("registrer respet nummer " + h + " hos pasient " + pasient.getNavn());
                
                System.out.println(i);

            }
        }
        
        //</editor-fold>
        
        //</editor-fold>
                */
        Login login = new Login(register);

        login.setLocationRelativeTo(null);
    }

    /**
     * Returnerer en liste over alle preparater
     * @return prepliste - liste over alle preparater
     */
    public static LinkedList<Preparat> getListe() {
        return prepliste;
    }

    /**
     * get-metode for Reseptmap
     * @return reseptregister - alle resepter i systemet
     */
    public static Reseptmap getReseptliste() {
        return reseptregister;
    }

    /**
     * get-metode for preparatregister
     * @return preparatregister - alle preparater i systemet
     */
    public static Preparatregister getPreparatregister() {
        return preparatregister;
    }

    /**
     * Lagrer personregisteret til fil, fanger opp en NotSerializibleException dersom objektet ikke er serialiserbart
     * fanger opp IOException dersom registerfilen ikke finnes
     */
    public static void lagreRegister() {
        try (ObjectOutputStream utfil = new ObjectOutputStream(new FileOutputStream("register.txt"))) {
            utfil.writeObject(register);
            System.out.println("Register ble lagret!");
        } catch (NotSerializableException ns) {
            System.out.println("Class: Login,Method: lagreRegister, Exception: NotSerializableExeption");
        } catch (IOException ioe) {
            System.out.println("Class: Login,Method: lagreRegister, Exception: IOException");
        }
    }

    /**
     * Laster opp personregister. 
     * ClassNotFoundException dersom filen ikke finnes, da opprettes et nytt register.
     * FileNotFoundException dersom filen ikke blir funnet, da opprettes et nytt register.
     * IOExceptionderrsom registerfilen ikke finnes.
     */
    public static void lastRegister() {
        try (ObjectInputStream innfil = new ObjectInputStream(new FileInputStream("register.txt"))) {
            register = (Register) innfil.readObject();
            System.out.println("Legemap ble lastet!");
        } catch (ClassNotFoundException cnfe) {
            register = new Register();
            System.out.println("Class: Login, Method: lastLegemap(), Exception: ClassNotFoundException, Lager ny legemap.data");
        } catch (FileNotFoundException fnfe) {
            System.out.println("Class: Login, Method: lastLegemap(), Exception: FileNotFoundException, Lager ny legemap.data");
            register = new Register();
        } catch (IOException ioe) {
            System.out.println("Class: Login, Method: lastLegemap(), Exception: IOException, Lager ny legemap.data");
            register = new Register();
        }
    }

    /**
     * Lagrer reseptregister til fil, fanger opp en NotSerializibleException dersom objektet ikke er serialiserbart
     * fanger opp IOException dersom registerfilen ikke finnes
     */
    public static void lagreReseptregister() {
        try (ObjectOutputStream utfil = new ObjectOutputStream(new FileOutputStream("reseptregister.txt"))) {
            utfil.writeObject(reseptregister);
            System.out.println("REseptregister ble lagret!");
        } catch (NotSerializableException ns) {
            System.out.println("Class: Login,Method: reseptregister, Exception: NotSerializableExeption");
        } catch (IOException ioe) {
            System.out.println(register == null);
            System.out.println("Class: Login,Method: reseptereigster, Exception: IOException");
        }
    }

    /**
     * Laster opp reseptregister. 
     * ClassNotFoundException dersom filen ikke finnes, da opprettes et nytt register.
     * FileNotFoundException dersom filen ikke blir funnet, da opprettes et nytt register.
     * IOExceptionderrsom registerfilen ikke finnes.
     */
    public static void lastReseptregister() {
        try (ObjectInputStream innfil = new ObjectInputStream(new FileInputStream("reseptregister.txt"))) {
            reseptregister = (Reseptmap) innfil.readObject();
            System.out.println("Reseptregister ble lastet!");
        } catch (ClassNotFoundException cnfe) {
            reseptregister = new Reseptmap();
            System.out.println("Class: Login, Method: reseptregister(), Exception: ClassNotFoundException, Lager ny legemap.data");
        } catch (FileNotFoundException fnfe) {
            System.out.println("Class: Login, Method: reseptregister(), Exception: FileNotFoundException, Lager ny legemap.data");
            reseptregister = new Reseptmap();
        } catch (IOException ioe) {
            System.out.println("Class: Login, Method: reseptregister(), Exception: IOException, Lager ny legemap.data");
            reseptregister = new Reseptmap();
        }
    }

}//class Reseptregister
