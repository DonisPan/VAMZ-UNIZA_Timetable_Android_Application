import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Subjects {
    PAS("Pravdepodobnosť a štatistika","PaS", Arrays.asList("Alžbeta Klaudinyová","Ida Stankovianska","Dalibor Gonda"),SubjectType.POVINNY),
    DS("Databázové systémy", "DS", Arrays.asList("Michal Kvet", "Ján Rabčan", "Veronika Šaligová", "Patrik Rusnák", "Miroslav Potčár"), SubjectType.POVINNY),
    AUS("Algoritmy a údajové Štruktúry", "AUS", Arrays.asList("Miroslav Kvaššay", "Michal Varga", "Marek Kvet"), SubjectType.POVINNY),
    DO("Diskrétna optymalizácia", "DO", Arrays.asList("Jaroslav Janáček", "Ľuboš Buzna", "Peter Czimermann", "Michal Koháni"), SubjectType.POVINNY),
    JCN("Jazyk C# a .net", "JCN", Collections.singletonList("Štefan Toth"), SubjectType.POVINNEVOLITELNY),
    VAMZ("Vývoj aplikácií pre mobilné zariadenia", "VAMZ", Arrays.asList("Patrik Hrkút", "Michal Ďuračík"), SubjectType.POVINNEVOLITELNY),
    AJ("Anglický jazyk", "AJ", Arrays.asList("Lenka Môcková", "Michal Mašlej"), SubjectType.VOLITELNY),
    ;

    private final String name;
    private final String shorter;
    private final List<String> teachers;
    private final SubjectType subjectType;
    Subjects(String name, String shorter, List<String> teachers, SubjectType subjectType) {
        this.name = name;
        this.shorter = shorter;
        this.teachers = teachers;
        this.subjectType = subjectType;
    }

    public String getName() {
        return name;
    }
    public String getShorter() {
        return this.shorter;
    }
    public SubjectType getSubjectType() {
        return this.subjectType;
    }
    public List<String> getTeachers() {
        return this.teachers;
    }
}
enum SubjectType {
    POVINNY,
    POVINNEVOLITELNY,
    VOLITELNY
}

