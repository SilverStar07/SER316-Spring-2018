package main.java.memoranda;
import java.util.Vector;

//import main.java.memoranda.NoteListImpl.Day;
//import main.java.memoranda.NoteListImpl.Month;
//import main.java.memoranda.NoteListImpl.NoteElement;
import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

public class DateInfo {
    static class Year {
        Element yearElement = null;

        public Year(Element el) {
            yearElement = el;
        }

        public int getValue() {
            return new Integer(yearElement.getAttribute("year").getValue()).intValue();
        }

        public Month getMonth(int m) {
            Elements ms = yearElement.getChildElements("month");
            String mm = new Integer(m).toString();
            for (int i = 0; i < ms.size(); i++)
                if (ms.get(i).getAttribute("month").getValue().equals(mm))
                    return new Month(ms.get(i));
            //return createMonth(m);
            return null;
        }

        public Month createMonth(int m) {
            Element el = new Element("month");
            el.addAttribute(new Attribute("month", new Integer(m).toString()));
            yearElement.appendChild(el);
            return new Month(el);
        }

        public Vector getMonths() {
            Vector v = new Vector();
            Elements ms = yearElement.getChildElements("month");
            for (int i = 0; i < ms.size(); i++)
                v.add(new Month(ms.get(i)));
            return v;
        }

        public Element getElement() {
            return yearElement;
        }

    }

    static class Month {
        Element mElement = null;

        public Month(Element el) {
            mElement = el;
        }

        public int getValue() {
            return new Integer(mElement.getAttribute("month").getValue()).intValue();
        }

        public Day getDay(int d) {
            if (mElement == null)
                return null;
            Elements ds = mElement.getChildElements("day");
            String dd = new Integer(d).toString();
            for (int i = 0; i < ds.size(); i++)
                if (ds.get(i).getAttribute("day").getValue().equals(dd))
                    return new Day(ds.get(i));
            //return createDay(d);
            return null;
        }

        public Day createDay(int d) {
            Element el = new Element("day");
            el.addAttribute(new Attribute("day", new Integer(d).toString()));
/*            el.addAttribute(
                new Attribute(
                    "date",
                    new CalendarDate(
                        d,
                        getValue(),
                        new Integer(((Element) mElement.getParent()).getAttribute("year").getValue()).intValue())
                        .toString()));
*/
            mElement.appendChild(el);
            return new Day(el);
        }

        public Vector getDays() {
            if (mElement == null)
                return null;
            Vector v = new Vector();
            Elements ds = mElement.getChildElements("day");
            for (int i = 0; i < ds.size(); i++)
                v.add(new Day(ds.get(i)));
            return v;
        }

        public Element getElement() {
            return mElement;
        }

    }

    
    /*
     * private class Day
     */
     
    static class Day {
        Element dEl = null;

        public Day(Element el) {
            dEl = el;
            // Added to fix old '.notes' XML format 
            // Old-style XML is converted on the fly [alexeya]
            if (dEl.getAttribute("date") != null) {
                Attribute dAttr = dEl.getAttribute("date");
                Attribute tAttr = dEl.getAttribute("title");
                Element nEl = new Element("note");
                String date = dAttr.getValue().replace('/', '-');
                nEl.addAttribute(new Attribute("refid", date));
                nEl.addAttribute(new Attribute("title", tAttr.getValue()));
                dEl.appendChild(nEl);
                dEl.removeAttribute(dAttr);             
                dEl.removeAttribute(tAttr);
            }
        }

        public int getValue() {
            return new Integer(dEl.getAttribute("day").getValue()).intValue();
        }

        /*public Note getNote() {
            return new NoteImpl(dEl);
        }*/
        
        public NoteElement getNote(String d) {
            if (dEl == null) 
                return null;
            Elements ne = dEl.getChildElements("note");
            
            for (int i = 0; i < ne.size(); i++)
                if (ne.get(i).getAttribute("refid").getValue().equals(d))
                    return new NoteElement(ne.get(i));
            //return createDay(d);
            return null;
        }

        public NoteElement createNote(String d) {
            Element el = new Element("note");
//          el.addAttribute(new Attribute("refid", d));
/*            el.addAttribute(new Attribute("day", new Integer(d).toString()));
                        el.addAttribute(
                new Attribute(
                    "date",
                    new CalendarDate(
                        10,
                        10,
                        2004).toString()));
*/                      
            dEl.appendChild(el);
            return new NoteElement(el);
        }

        public Vector getNotes() {
            if (dEl == null)
                return null;
            Vector v = new Vector();
            Elements ds = dEl.getChildElements("note");
            for (int i = 0; i < ds.size(); i++)
                v.add(new NoteElement(ds.get(i)));                                    
            return v;
        }

        public Element getElement() {
            return dEl;
        }
    }
    
    static class NoteElement {
        Element nEl;
        
        public NoteElement(Element el) {
            nEl = el;
        }
        
        public Element getElement() {
            return nEl;
        }
    }
}
