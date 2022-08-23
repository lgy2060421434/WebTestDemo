package framework.po;

import java.util.HashMap;
/*
保存所有的po，poName，PoBasePage实例
 */
public class POStore {
    private static POStore store;
    private HashMap<String,POBasePage> poStore=new HashMap<>();
//    直接调用，减少内存损耗
    public static POStore getInstance(){
        if (store == null){
            store=new POStore();
        }
        return store;
    }
    public void setPO(String name,POBasePage page){
        poStore.put(name,page);
    }
    public POBasePage getPO(String name){
        return poStore.get(name);
    }
}
