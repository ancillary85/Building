/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.Arrays;
import java.util.EnumSet;

/**
 *
 * @author Alecto
 */


public class AntTaskBuilder {
 
    public static Task dig() {
        Task DIG = new Task("Dig", 1, "Digging",
            new Trait[]{new Trait("Patience", -1, TraitBuilder.personal_resource())}, 
            null,
            new Trait[]{new Trait("Space", 1, TraitBuilder.resource())},
            "Expand the colony");
        return DIG;
    }
    public static Task forage() {
        Task FORAGE = new Task("Forage", 2, "Foraging",
            new Trait[]{new Trait("Machetes", -1, TraitBuilder.resource())}, 
            null, 
            new Trait[]{new Trait("Food", 1, TraitBuilder.resource())},
            "Search for food");
        return FORAGE;
    }
    public static Task hunt() {
        Task HUNT = new Task("Hunt", 3, "Hunting",
            new Trait[]{new Trait("Stamina", -1, TraitBuilder.personal_resource())}, 
            new Trait[]{new Trait("Stamina", 0, TraitBuilder.reqGreaterThanPersonal())}, 
            new Trait[]{new Trait("Food", 2, TraitBuilder.resource())},
            "Hunt for food");
        return HUNT;
    }
    public static Task weave() {
        Task WEAVE = new Task("Weave silk", 3, "Weaving",
            null,
            null,
            new Trait[]{new Trait("Silk", 1, TraitBuilder.resource())},
            "Produce silk for crafting");
        return WEAVE;
    }
    public static Task larvaCare() {
        Task LARVA_CARE = new Task("Tend to Larva", 1, "Larva tending",
            new Trait[]{new Trait("Food", -1, TraitBuilder.resource())}, 
            new Trait[]{new Trait("Food", 0, TraitBuilder.reqGreaterThanResource()), new Trait("Larva", 0, "Larva in need", TraitBuilder.reqGreaterThanCreation())}, 
            new Trait[]{new Trait("Larva Stamina", 1, TraitBuilder.resourceHidden())}, 
            "Care for growing larval ants");
        return LARVA_CARE;
    }
    public static Task eat() {
        Task EAT = new Task("Eat", 2, "Eating",
            new Trait[]{new Trait("Food", -1, TraitBuilder.resource())}, 
            new Trait[]{new Trait("Food", 0, TraitBuilder.reqGreaterThanResource())}, 
            new Trait[]{new Trait("Stamina", 4, TraitBuilder.personal_resource())},
            "Eat some food");
        return EAT;
    }
    public static Task rest() {
        Task REST = new Task("Rest", 1, "Resting",
            null, 
            null, 
            new Trait[]{new Trait("Stamina", 1, TraitBuilder.personal_resource())},
            "Take a rest");
        return REST;
    }
    
    public static Task fight() {
        Task FIGHT = new Task("Fight", 1, "Fighting",
            new Trait[]{new Trait("Stamina", -2, TraitBuilder.personal_resource())},
            new Trait[]{new Trait("Stamina", 1, TraitBuilder.reqGreaterThanPersonal())},
            new Trait[]{new Trait("Enemies", -1, TraitBuilder.resource()), new Trait("Stamina", -1, TraitBuilder.personal_resource())},
            "Fight an enemy");
        return FIGHT;
    }
    
    public static Task brood() {
        Task BROOD = new Task("Brood", 2, "Laying eggs",
            new Trait[]{new Trait("Patience", -1, TraitBuilder.personal_resource())}, 
            null,
            new Trait[]{new Trait("Egg", 2, TraitBuilder.creation())},
            "Lay new eggs");
        return BROOD;
    }
    
    public static Task eatEgg() {
        Task EATEGG = new Task("Eat an Egg", 1, "Eating an egg", 
            null,
            new Trait[]{new Trait("Egg", 0, "Egg to eat", TraitBuilder.reqGreaterThanCreation())},
            new Trait[]{new Trait("Egg", -1, TraitBuilder.uncreate())},
            "Eat an egg to regain strength");
        
        return EATEGG;
    }
    
    public static Task eggHatch() {
        Trait result = new Trait("Larva", 1, TraitBuilder.autoActiveChangeUncan());
        result.setDesc(AntBuilder.LARVA);
        Task eggGrow = new Task("Hatch", 1, "Incubating", 
            null,
            null,
            new Trait[]{result},
            "This egg will hatch soon");
        
        return eggGrow;
    }
    
    public static Task workerPupaHatch() {
        Trait result = new Trait("Worker", 1, TraitBuilder.autoActiveChangeUncan());
        result.setDesc(AntBuilder.WORKER + "," + AntBuilder.ANT);
        Task eggGrow = new Task("Metamorphose", 1, "Worker Metamorphosizing", 
            null,
            null,
            new Trait[]{result},
            "This pupa will hatch soon");
        
        return eggGrow;
    }
    
    public static Task pupateWorker() {
        Trait result = new Trait("Pupa", 1, TraitBuilder.active_change());
        result.setDesc(AntBuilder.PUPA + "," + AntBuilder.WORKER);
        Task eggGrow = new Task("Pupate to Worker", 1, "Cocooning", 
            null,
            null,
            new Trait[]{result},
            "Spin a cocoon to change to a worker");
        
        return eggGrow;
    }
    
    public static Task soldierPupaHatch() {
        Trait result = new Trait("Soldier", 1, TraitBuilder.autoActiveChangeUncan());
        result.setDesc(AntBuilder.SOLDIER + "," + AntBuilder.ANT);
        Task eggGrow = new Task("Metamorphose", 1, "Soldier Metamorphosizing", 
            null,
            null,
            new Trait[]{result},
            "This pupa will hatch soon");
        
        return eggGrow;
    }
    
    public static Task pupateSoldier() {
        Trait result = new Trait("Pupa", 1, TraitBuilder.active_change());
        result.setDesc(AntBuilder.PUPA + "," + AntBuilder.SOLDIER);
        Task eggGrow = new Task("Pupate to Soldier", 1, "Cocooning", 
            null,
            null,
            new Trait[]{result},
            "Spin a cocoon to change to a soldier");
        
        return eggGrow;
    }
    
    public static Task dronePupaHatch() {
        Trait result = new Trait("Drone", 1, TraitBuilder.autoActiveChangeUncan());
        result.setDesc(AntBuilder.DRONE + "," + AntBuilder.ANT);
        Task eggGrow = new Task("Metamorphose", 1, "Drone Metamorphosizing", 
            null,
            null,
            new Trait[]{result},
            "This pupa will hatch soon");
        
        return eggGrow;
    }
    
    public static Task pupateDrone() {
        Trait result = new Trait("Pupa", 1, TraitBuilder.active_change());
        result.setDesc(AntBuilder.PUPA + "," + AntBuilder.DRONE);
        Task eggGrow = new Task("Pupate to Drone", 1, "Cocooning", 
            null,
            null,
            new Trait[]{result},
            "Spin a cocoon to change to a drone");
        
        return eggGrow;
    }
    
    public static Task queenPupaHatch() {
        Trait result = new Trait("Queen", 1, TraitBuilder.autoActiveChangeUncan());
        result.setDesc(AntBuilder.QUEEN + "," + AntBuilder.ANT);
        Task eggGrow = new Task("Metamorphose", 1, "Queen Metamorphosizing", 
            null,
            null,
            new Trait[]{result},
            "This pupa will hatch soon");
        
        return eggGrow;
    }
    
    public static Task pupateQueen() {
        Trait result = new Trait("Pupa", 1, TraitBuilder.active_change());
        result.setDesc(AntBuilder.PUPA + "," + AntBuilder.QUEEN);
        Task eggGrow = new Task("Pupate to Queen", 1, "Cocooning", 
            null,
            null,
            new Trait[]{result},
            "Spin a cocoon to change to a queen");
        
        return eggGrow;
    }
    
    public static Task[] antTasks() {
        return new Task[]{dig(), forage(), hunt(), eat(), rest()};
    }
    
    public static Task[] workerTasks() {
        return new Task[]{larvaCare()};
    }
    
    public static Task[] soldierTasks() {
        return new Task[]{fight()};
    }

    public static Task[] droneTasks() {
        return antTasks();
    }

    public static Task[] queenTasks() {
        Task[] queenTasks = new Task[]{brood(), eatEgg()};
        
        return queenTasks;
    }

    public static Task[] eggTasks() {
        return new Task[]{eggHatch()};
    }

    public static Task[] larvaTasks() {
        return new Task[]{pupateWorker(), pupateSoldier(), pupateDrone(), pupateQueen(), eat(), rest(), weave()};
    }

    public static Task[] pupaTasks() {
        return new Task[]{workerPupaHatch(), soldierPupaHatch(), dronePupaHatch(), queenPupaHatch()};
    }
    
    public static Task[] getPupaTask(String id) {
        switch(id) {
            case AntBuilder.WORKER: return new Task[]{workerPupaHatch()};
            case AntBuilder.SOLDIER: return new Task[]{soldierPupaHatch()};
            case AntBuilder.DRONE: return new Task[]{dronePupaHatch()};
            case AntBuilder.QUEEN: return new Task[]{queenPupaHatch()};
            default: return new Task[]{workerPupaHatch()};
        }
    }
}
