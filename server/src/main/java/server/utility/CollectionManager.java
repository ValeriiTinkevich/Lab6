package server.utility;


import common.data.SpaceMarine;
import server.App;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.LinkedList;

/**
 * Collection manager.
 * Collection used is LinkedList
 * Elements are of class Space marine
 */
public class CollectionManager {
    public LinkedList<SpaceMarine> spaceMarineCollection;
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final ZonedDateTime creationDate;
    private FileManager collectionFileManager;


    /**
     * Collection manager constructor.
     */
    public CollectionManager(FileManager fileManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        spaceMarineCollection = new LinkedList<>();
        creationDate = ZonedDateTime.now();
        this.collectionFileManager = fileManager;

        loadCollection();
    }

    /**
     * Creation date getter.
     * @return Creation date of collection manager.
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Get collection.
     * @return Linked list of space marines.
     */
    public LinkedList<SpaceMarine> getSpaceMarineCollection() {
        return spaceMarineCollection;
    }

    /**
     * Collection setter
     * @param spaceMarineCollection Space marines linked list.
     */
    public void setSpaceMarineCollection(LinkedList<SpaceMarine> spaceMarineCollection) {
        this.spaceMarineCollection = spaceMarineCollection;
    }

    /**
     * Get element by id.
     * @param id Id of needed element.
     * @return Returns space marine with given id.
     */
    public SpaceMarine getById(long id) {
        for(SpaceMarine spaceMarine: spaceMarineCollection)  {
            if(spaceMarine.getId() == id) return spaceMarine;
        }
        return null;
    }

    /**
     * Get size of a collection.
     * @return Collection size.
     */
    public int getSize() {
        return spaceMarineCollection.size();
    }

    /**
     * Generates id for new element.
     * @return generated id
     */
    public long generateNewIdForCollection() {
        long id = spaceMarineCollection.stream()
                .mapToLong(SpaceMarine::getId)
                .filter(organization -> organization >= 0)
                .max().orElse(0);
        return id + 1;
    }

    /**
     * Adds element to collection
     * @param spaceMarine element that needs to be added
     */
    public void addToCollection(SpaceMarine spaceMarine) {
        spaceMarineCollection.add(spaceMarine);
    }

    /**
     * Update element by id
     * @param id id that needs to be updated
     * @param spaceMarine new element
     */
    public void update(long id, SpaceMarine spaceMarine)  {
        int index = spaceMarineCollection.indexOf(getById(id));
        spaceMarineCollection.set(index, spaceMarine);


    }

    /**
     * Remove element from collection
     * @param spaceMarine element that needs to be removed.
     */
    public void removeFromCollection(SpaceMarine spaceMarine) {
        spaceMarineCollection.remove(spaceMarine);
    }

    /**
     * Remove element from collection by id.
     * @param id id of element to remove
     */
    public void removeByIDFromCollection(int id) {
        spaceMarineCollection.stream()
                .filter(organization -> organization.getId() == id)
                .findFirst()
                .ifPresent(this::removeFromCollection);
    }

    /**
     * Remove element from collection by index.
     * @param index Index of a space marine that needs to be removed.
     */
    public void removeAtIndex(int index) {
        spaceMarineCollection.remove(index);
    }

    /**
     * Saves the collection to file.
     */
    public void saveCollection() {
        collectionFileManager.saveCollection(spaceMarineCollection);
        lastSaveTime = LocalDateTime.now();
        App.logger.info("Collection saved successfully");
    }

    /**
     * Loads the collection from file.
     */
    private void loadCollection() {
        spaceMarineCollection = collectionFileManager.readCollection();
        lastInitTime = LocalDateTime.now();
        App.logger.info("Collection loaded successfully");
    }
}

