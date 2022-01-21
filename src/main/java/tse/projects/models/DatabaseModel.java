package tse.projects.models;

import tse.projects.utils.Condition;

import java.util.List;


public interface DatabaseModel {

    public default Boolean passConditions(List<Condition> conditions) throws NoSuchFieldException, IllegalAccessException {
        boolean queried = true;

        conditionLoop:
        for (Condition condition : conditions) {
            String columnValue = this.getClass().getDeclaredField(condition.column).get(this).toString();

            switch (condition.operator) {
                case "=":
                    if (!columnValue.equals(condition.value)) {
                        queried = false;
                        break conditionLoop;
                    }
                    break;
                case "<":
                    if (Double.parseDouble(columnValue) >= Double.parseDouble(condition.value)) {
                        queried = false;
                        break conditionLoop;
                    }
                    break;
                case "<=":
                    if (Double.parseDouble(columnValue) > Double.parseDouble(condition.value)) {
                        queried = false;
                        break conditionLoop;
                    }
                    break;
                case ">":
                    if (Double.parseDouble(columnValue) <= Double.parseDouble(condition.value)) {
                        queried = false;
                        break conditionLoop;
                    }
                    break;
                case ">=":
                    if (Double.parseDouble(columnValue) < Double.parseDouble(condition.value)) {
                        queried = false;
                        break conditionLoop;
                    }
                    break;
            }
        }

        return queried;
    }
}
