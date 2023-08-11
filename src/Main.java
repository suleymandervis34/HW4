import java.util.Random;

public class Main {
    public static int bossHealth = 750;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {270, 280, 250, 400, 600, 250, 300, 200};
    public static int[] heroesDamage = {10, 15, 20, 0, 5, 5, 4, 9};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medical", "Golem", "Lucky", "Berserk", "Thor"};
    public static int roundNumber;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        berserkReturnDamage();

        chooseBossDefence();
        medicHeal();
        bossHits();

        golem();
        luckyLuck();

        heroesHit();
        thorStun();
        printStatistics();

    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                heroesHealth[i] -= bossDamage; // => heroesHealth[i] = heroesHealth[i] - bossDamage;
                if (heroesHealth[i] < 0) {
                    heroesHealth[i] = 0;
                }
                if (heroesHealth[6] < 0) {
                    heroesHealth[6] = 0;
                }
            }
        }
    }


    public static void berserkReturnDamage() {
        int berserkIndex = 6;
        int blockDamage = bossDamage / 5;
        heroesHealth[berserkIndex] -= blockDamage;
        bossDamage += blockDamage;
        System.out.println(heroesAttackType[berserkIndex] + " blocked " + blockDamage +
                " boss damage and returned it");
    }


    public static void golem() {
        int takenDamageFromBoss = 0;
        boolean onlyGetsOneShot = false;

        for (int x = 0; x < heroesHealth.length; x++) {
            if (heroesHealth[4] > 0 && bossHealth >= 0) {
                if (heroesAttackType[x] != "Golem") {
                    if (!onlyGetsOneShot) {
                        onlyGetsOneShot = true;
                        takenDamageFromBoss = bossDamage % heroesHealth[x];
                        heroesHealth[4] -= takenDamageFromBoss;
                        System.out.println(heroesAttackType[4] + " took boss damage " +
                                takenDamageFromBoss + " from hero " + heroesAttackType[x]);
                    }
                }
            }
        }
    }

    public static void thorStun() {
        Random random = new Random();
        boolean stun = random.nextBoolean();
        if (stun) {
            System.out.println(heroesAttackType[7] + " stunned the boss!");
            roundNumber++;
        }
    }

    public static void luckyLuck() {
        Random random = new Random();
        int luckyChance = random.nextInt(10);
        if (luckyChance < 1) {
            System.out.println(heroesAttackType[5] + " dodged the boss attack!");
        } else {
            heroesHealth[5] -= bossDamage;
            if (heroesHealth[5] < 0) {
                heroesHealth[5] = 0;
            }
            System.out.println(heroesAttackType[5] + " got hit by the boss");
        }
    }

    public static void medicHeal() {   
        int healPoint = 100;
        boolean justOneHealInOneRound = false;

        for (int x = 0; x < heroesHealth.length; x++) {
            if (heroesHealth[3] > 0 && bossHealth >= 0) {
                if (heroesHealth[x] < 100 && heroesHealth[x] > 0) {
                    if (!justOneHealInOneRound) {
                        if (heroesAttackType[x] != "Medic") {
                            justOneHealInOneRound = true;
                            heroesHealth[x] += healPoint;
                            System.out.println(heroesAttackType[3] + "  healed hero: " + heroesAttackType[x]);
                        }
                    }
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (bossDefence == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                bossHealth -= damage; // => bossHealth = bossHealth - heroesDamage[i];
                if (bossHealth < 0) {
                    bossHealth = 0;
                }
            }
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " +
                (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] +
                    " damage: " + heroesDamage[i]);
        }
    }


}