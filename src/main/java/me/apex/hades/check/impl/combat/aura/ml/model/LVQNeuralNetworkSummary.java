package me.apex.hades.check.impl.combat.aura.ml.model;

import lombok.Getter;

//Credits to Nova41
public class LVQNeuralNetworkSummary {

    @Getter private double stepSize;
    @Getter private int epoch, inputs, outputs;

    LVQNeuralNetworkSummary(int epoch, double stepSize, int inputs, int outputs) {
        this.epoch = epoch;
        this.stepSize = stepSize;
        this.inputs = inputs;
        this.outputs = outputs;
    }

}
