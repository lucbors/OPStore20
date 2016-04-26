package com.eproseed.opstore.mobile.beans;

import com.eproseed.opstore.mobile.model.pojos.Store;
import com.eproseed.opstore.mobile.model.pojos.StoreList;

import java.util.ArrayList;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import javax.el.ValueExpression;

import oracle.adfmf.amx.event.ValueChangeEvent;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.java.beans.PropertyChangeListener;
import com.eproseed.opstore.mobile.model.ServicesWrapper;

import oracle.adfmf.java.beans.ProviderChangeListener;
import oracle.adfmf.java.beans.ProviderChangeSupport;


/*
 * Copyright � AuraPlayer 2013 All Rights Reserved.
 * No part of this source code may be reproduced without AuraPlayer's express consent.
 */

public class StoreLocator {
    
    private static int searchBySelection = 0;
    private  static int selectedStoreIndex = 0;
    private  String selectedStoreName = "";
    
    public static Store selectedStore;

    private static StoreList s_fullStoresList = null;
    private static String currentFilter="";
    public static boolean searchOn = false;
 
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private ProviderChangeSupport providerChangeSupport = new ProviderChangeSupport(this);

    
    public void addProviderChangeListener(ProviderChangeListener l) {
        providerChangeSupport.addProviderChangeListener(l);
    }

    public void removeProviderChangeListener(ProviderChangeListener l) {
        providerChangeSupport.removeProviderChangeListener(l);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }


    public StoreLocator() {
        init();
        searchBySelection = 0;
    }
    public static void init(){
        if (s_fullStoresList==null)
        {
            ServicesWrapper srvwr = new ServicesWrapper();
            s_fullStoresList = new StoreList();
           // add the dummies to the list
            s_fullStoresList.setStoresDummy();
            // get also data from webservice
            srvwr.getStoresListByStateWS("CA");
    }
    }

    public  void clearStoreList()
    {
        s_fullStoresList.clear();
        providerChangeSupport.fireProviderRefresh("fullStoresList");
    }

    public  void addStore(Store store){
        s_fullStoresList.add(store);
        providerChangeSupport.fireProviderRefresh("fullStoresList");
    }



    public static void addStoreToList(Store store){
        s_fullStoresList.add(store);
    }
        
    public void setSearchBySelection(int searchBySelection) {
        int oldSearchBySelection = this.searchBySelection;
        this.searchBySelection = searchBySelection;
        propertyChangeSupport.firePropertyChange("searchBySelection", oldSearchBySelection, searchBySelection);
    }

    public int getSearchBySelection() {
        return searchBySelection;
    }
  
    public void setSelectedStore(){
        Store oldSelectedStore = this.selectedStore;
        this.selectedStore = s_fullStoresList.getStoreById(selectedStoreIndex);
        propertyChangeSupport.firePropertyChange("selectedStore", oldSelectedStore, selectedStore);

        
    }

   public static Store getSelectedStore() {
            try{
                return s_fullStoresList.getStoreById(selectedStoreIndex);
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }

    public static Store s_getStoreByID(int id) {
        try{
            getFilteredStoresList();
            return s_fullStoresList.getStoreById(id);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public String selectStore() {
        try{



        }catch(Exception e){
            e.printStackTrace();
        }
        searchBySelection = 0;
        return "refresh";
    }

    public static Store[] getFilteredStoresList() {
        try{
            if (s_fullStoresList==null)
            {                
                init();
            }

            return s_fullStoresList.getStores();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public void setCurrentFilter(String currentFilter) {
        String oldCurrentFilter = this.currentFilter;
        this.currentFilter = currentFilter;
        propertyChangeSupport.firePropertyChange("currentFilter", oldCurrentFilter, this.currentFilter);
        FilterStoreByName();
    
    }

    public static String getCurrentFilter() {
        return currentFilter;
    }

    public String FilterStoreByName() {
        try{
            if (currentFilter != null  && currentFilter.length()>0) {
                String filter = currentFilter.toLowerCase();
                ArrayList fullStoresList = s_fullStoresList.getStoresArrayList();
                int count = fullStoresList.size();
                for (int index=0;index<count;index++) 
                {
                    Store store = (Store)fullStoresList.get(index);
                    if (store.getName().toLowerCase().startsWith(filter)) {
                        store.setFilter(false);
                    }
                    else{
                        store.setFilter(true);
                    }
                }
            }
            else {
                ArrayList fullStoresList = s_fullStoresList.getStoresArrayList();
                int count = fullStoresList.size();
                for (int index=0;index<count;index++) 
                {
                    Store store = (Store)fullStoresList.get(index);
                    store.setFilter(false);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return "refresh";
    }

    public void setSearchOn(boolean searchOn) {
        boolean oldSearchOn = this.searchOn;
        this.searchOn = searchOn;
        propertyChangeSupport.firePropertyChange("searchOn", oldSearchOn, searchOn);
    }

    public static boolean isSearchOn() {
        return searchOn;
    }

    public String ResetFilterByName() {
        ArrayList fullStoresList = s_fullStoresList.getStoresArrayList();
        int count = fullStoresList.size();
        for (int index=0;index<count;index++) 
        {
            Store store = (Store)fullStoresList.get(index);
            store.setFilter(false);
        }
        return "refresh";
    }

    public static void s_setSelectedStoreIndex(int selectedStoreIndex) {
          StoreLocator.selectedStoreIndex = selectedStoreIndex;
      }
    
    public void setSelectedStoreIndex(int selectedStoreIndex) {
        int oldSelectedStoreIndex = this.selectedStoreIndex;
        this.selectedStoreIndex = selectedStoreIndex;
        propertyChangeSupport.firePropertyChange("selectedStoreIndex", oldSelectedStoreIndex, selectedStoreIndex);
        selectStore();
       
    }

    public int getSelectedStoreIndex() {
        return this.selectedStoreIndex;
    }


    public void setSelectedStoreName(String selectedStoreName) {
        this.selectedStoreName = selectedStoreName;
    }

    public  String getSelectedStoreName() {
        return this.selectedStoreName;
    }


}
