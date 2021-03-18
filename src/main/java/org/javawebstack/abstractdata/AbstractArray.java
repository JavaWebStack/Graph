package org.javawebstack.abstractdata;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.*;
import java.util.stream.Stream;

public class AbstractArray implements AbstractElement, Iterable<AbstractElement> {

    private final List<AbstractElement> elements;

    public boolean isArray() {
        return true;
    }

    public AbstractArray array() {
        return this;
    }

    public AbstractObject object(int index) {
        return get(index).object();
    }

    public AbstractArray array(int index) {
        return get(index).array();
    }

    public AbstractPrimitive primitive(int index) {
        return get(index).primitive();
    }

    public String string(int index) {
        return get(index).string();
    }

    public Boolean bool(int index) {
        return get(index).bool();
    }

    public Number number(int index) {
        return get(index).number();
    }

    public AbstractArray add(AbstractElement element) {
        if (element == null)
            element = AbstractNull.INSTANCE;
        elements.add(element);
        return this;
    }

    public AbstractArray(){
        elements = new ArrayList<>();
    }

    public AbstractArray(Object[] objects){
        this();
        for (Object o : objects) {
            if (o instanceof AbstractElement)
                elements.add((AbstractElement) o);
            else
                elements.add(AbstractElement.fromAbstractObject(o));
        }
    }

    public AbstractArray(Collection<Object> abstractElements){
        this(abstractElements.toArray());
    }

    public AbstractArray addNull() {
        return add(AbstractNull.INSTANCE);
    }

    public AbstractArray add(Number value) {
        if (value == null)
            return addNull();
        return add(new AbstractPrimitive(value));
    }

    public AbstractArray add(Boolean value) {
        if (value == null)
            return addNull();
        return add(new AbstractPrimitive(value));
    }

    public AbstractArray add(String value) {
        if (value == null)
            return addNull();
        return add(new AbstractPrimitive(value));
    }

    public AbstractArray setNull(int i) {
        return set(i, AbstractNull.INSTANCE);
    }

    public AbstractArray set(int i, AbstractElement element){
        if(element == null)
            return setNull(i);
        while (elements.size() <= i)
            addNull();
        elements.set(i, element);
        return this;
    }

    public AbstractArray set(int i, Number value){
        if(value == null)
            return setNull(i);
        return set(i, new AbstractPrimitive(value));
    }

    public AbstractArray set(int i, Boolean value){
        if(value == null)
            return setNull(i);
        return set(i, new AbstractPrimitive(value));
    }

    public AbstractArray set(int i, String value){
        if(value == null)
            return setNull(i);
        return set(i, new AbstractPrimitive(value));
    }

    public AbstractArray remove(int i){
        elements.remove(i);
        return this;
    }

    public AbstractElement[] toArray(){
        return elements.toArray(new AbstractElement[0]);
    }

    public AbstractElement get(int i){
        return elements.get(i);
    }

    public Stream<AbstractElement> stream(){
        return elements.stream();
    }

    public int size() {
        return elements.size();
    }

    public boolean contains(Object o){
        for (AbstractElement element : elements) {
            if (o instanceof AbstractElement ? ((AbstractElement) o).toAbstractObject().equals(element.toAbstractObject()) : element.toAbstractObject().equals(o))
                return true;
        }
        return false;
    }

    public AbstractArray clear() {
        elements.clear();
        return this;
    }

    public Iterator<AbstractElement> iterator() {
        return elements.iterator();
    }

    public JsonElement toJson() {
        JsonArray array = new JsonArray();
        elements.forEach(e -> array.add(e.toJson()));
        return array;
    }

    public Object toAbstractObject() {
        List<Object> list = new ArrayList<>();
        elements.forEach(e -> list.add(e.toAbstractObject()));
        return list;
    }

    public static AbstractArray fromJson(JsonArray array){
        AbstractArray a = new AbstractArray();
        array.forEach(e -> a.add(AbstractElement.fromJson(e)));
        return a;
    }


    public static AbstractArray fromArray(Object[] objects){
        return new AbstractArray(objects);
    }

    public static AbstractArray fromList(Collection collection){
        return new AbstractArray(collection);
    }

    public Type getType() {
        return Type.ARRAY;
    }

    public AbstractObject object(){
        AbstractObject o = new AbstractObject();
        for(int i=0; i<size(); i++)
            o.set(String.valueOf(i), get(i));
        return o;
    }

    public Map<String[], Object> toTree(){
        return object().toTree();
    }

    public AbstractElement clone() {
        AbstractArray array = new AbstractArray();
        forEach(e -> array.add(e.clone()));
        return array;
    }

}
