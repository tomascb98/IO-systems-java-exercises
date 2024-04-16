package edu.epam.fop.lambdas.kamino;

import edu.epam.fop.lambdas.kamino.equipment.Equipment;
import edu.epam.fop.lambdas.kamino.equipment.EquipmentFactory;

import java.util.Iterator;

public class BatchPolicies {

  public interface BatchPolicy {

    CloneTrooper[] formBatchOf(CloneTrooper base, int count);
  }

  public static BatchPolicy getCodeAwarePolicy(String codePrefix, int codeSeed) {

    return (base, count) -> {
      CloneTrooper[] clones = new CloneTrooper[count];
      for(int i = 0 ; i < count; i++){
        clones[i] = KaminoFactory.growClone(codePrefix + "-" + String.format("%04d",codeSeed+i));
        if(base.getNickname() != null) clones[i].setNickname(base.getNickname());
        if(base.getEquipment() != null) clones[i].setEquipment(EquipmentFactory.orderTheSame(base.getEquipment()));
      }
      return clones;
    };
  }

  public static BatchPolicy addNicknameAwareness(Iterator<String> nicknamesIterator, BatchPolicy policy) {

    return (base, count) -> {
      CloneTrooper[] clones = KaminoFactory.formBatch(policy, base, count);
      int i = 0;
      while(nicknamesIterator.hasNext() && i < clones.length){
        String nickname = nicknamesIterator.next();
        clones[i].setNickname(nickname);
        i++;
      }
      return clones;
    };
  }

  public static BatchPolicy addEquipmentOrdering(Equipment equipmentExample, BatchPolicy policy) {
    return (base, count) -> {
      CloneTrooper[] clones = KaminoFactory.formBatch(policy, base, count);
      for(CloneTrooper clone : clones){
        clone.setEquipment(EquipmentFactory.orderTheSame(equipmentExample));
      }
      return clones;
    };
  }
}
