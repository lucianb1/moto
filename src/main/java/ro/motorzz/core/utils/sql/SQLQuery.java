package ro.motorzz.core.utils.sql;

/**
 * Created by Luci on 31-Jul-17.
 */
public class SQLQuery {

    private final String query;
    private final Object[] params;

    public SQLQuery(String query, Object[] params) {
        this.query = query;
        this.params = params;
    }

    public String getQuery() {
        return query;
    }

    public Object[] getParams() {
        return params;
    }

}
