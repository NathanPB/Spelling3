package cf.nathanpb.Spelling3.events;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nathanpb on 10/22/17.
 */
public class EventBus {
    private static ArrayList<ListenerRegistry> ListenersMap = new ArrayList<>();

    public static void register(Object o){
        Class c = o.getClass();
        if(o instanceof Class) c = (Class)o;
        Arrays.stream(c.getDeclaredMethods())
                .filter(m -> m.getParameterCount() == 1)
                .filter(m -> m.isAnnotationPresent(EventHandler.class))
                .filter(m -> {
                    for(Class c2 : m.getAnnotation(EventHandler.class).target()){
                        if(Arrays.asList(m.getParameterTypes()).get(0).isAssignableFrom(c2)) return true;
                    }
                    return false;
                })
                .forEach(m -> ListenersMap.add(new ListenerRegistry() {
                            private EventHandler annotation = m.getAnnotation(EventHandler.class);
                            @Override
                            public Class getListenerClass() {
                                return (Class) o;
                            }

                            @Override
                            public Method getMethod() {
                                return m;
                            }

                            @Override
                            public Integer getPriority() {
                                return annotation.priority();
                            }

                            @Override
                            public List<Class<? extends SpellingEvent>> getTargets() {
                                return Arrays.asList(annotation.target());
                            }
                            @Override
                            public Object getInstance() {
                                if(Modifier.isStatic(m.getModifiers())){
                                    return null;
                                }else{
                                    return o;
                                }
                            }
                        }));
        ListenersMap.sort((a, b) -> {
            if(a.getPriority() < b.getPriority()) return -1;
            if(a.getPriority() > b.getPriority()) return 1;
            return 0;
        });
    }

    public static void post(SpellingEvent e){
        ListenersMap.stream()
                .filter(a -> {
                    for(Class c : a.getTargets()) if(c.isAssignableFrom(e.getClass())) return true;
                    return false;
                })
                .forEachOrdered(a -> {
                    try {
                        Method m = a.getMethod();
                        if(!m.isAccessible()) m.setAccessible(true);
                        m.invoke(a.getInstance(), (Object) e);
                    }catch (Exception ex){ ex.printStackTrace();}
                });
    }

    private interface ListenerRegistry{
        Class getListenerClass();
        Method getMethod();
        Integer getPriority();
        List<Class<? extends SpellingEvent>> getTargets();
        Object getInstance();
    }
}
