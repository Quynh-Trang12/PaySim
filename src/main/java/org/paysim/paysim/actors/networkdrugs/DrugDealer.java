package org.paysim.paysim.actors.networkdrugs;

import sim.engine.SimState;

import org.paysim.paysim.PaySim;
import org.paysim.paysim.actors.Client;

public class DrugDealer extends Client {
    private double thresholdForCashOut;
    private double drugMoneyInAccount;

    public DrugDealer(PaySim paySim, double thresholdForCashOut) {
        super(paySim);
        this.thresholdForCashOut = thresholdForCashOut;
        this.drugMoneyInAccount = 0;
    }

    @Override
    public void step(SimState state) {
        PaySim paySim = (PaySim) state;
        int step = (int) paySim.schedule.getSteps();

        super.step(state);

        if (wantsToCashOutProfit(paySim)) {
            double amount = pickAmountCashOutProfit(paySim);
            super.handleCashOut(paySim, step, amount);
            drugMoneyInAccount -= amount;
        }
    }

    private boolean wantsToCashOutProfit(PaySim paySim) {
        return drugMoneyInAccount > thresholdForCashOut && paySim.random.nextBoolean(0.5);
    }

    private double pickAmountCashOutProfit(PaySim paySim) {
        double fraction = 0.8 + 0.2 * paySim.random.nextDouble();
        return thresholdForCashOut * fraction;
    }

    protected void addMoneyFromDrug(double amount) {
        drugMoneyInAccount += amount;
    }
}
