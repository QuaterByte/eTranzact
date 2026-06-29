package com.example.eTransact.utility;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.boot.model.relational.Sequence;
import org.hibernate.engine.jdbc.env.spi.IdentifierHelper;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;

public class MySequenceGenerator implements BeforeExecutionGenerator {

    private final Identifier sequenceName;
    private final String sqlSelectFrag;

    public MySequenceGenerator(MySequence mySequence, GeneratorCreationContext context){
       Database database = context.getDatabase();
       IdentifierHelper identifierHelper = database.getJdbcEnvironment().getIdentifierHelper();
       Sequence sequence = database.getDefaultNamespace().createSequence(
            identifierHelper.toIdentifier(mySequence.name()),
               (physicalName)->new Sequence(
                       null,
                       database.getDefaultNamespace().getPhysicalName().catalog(),
                       database.getDefaultNamespace().getPhysicalName().schema(),
                       physicalName,
                       mySequence.initialValue(),
                       mySequence.incrementSize()
               )
       );

       sequenceName = sequence.getName().getSequenceName();
       sqlSelectFrag = database.getDialect().getSequenceSupport().getSequenceNextValString(sequenceName.render(database.getDialect()));


    }

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o, Object o1, EventType eventType) {
        try{
            final PreparedStatement st = sharedSessionContractImplementor.getJdbcCoordinator().getStatementPreparer().prepareStatement(sqlSelectFrag);
            try{
                final ResultSet rs = sharedSessionContractImplementor.getJdbcCoordinator().getResultSetReturn().extract(st, sqlSelectFrag);
                try{
                    rs.next();
                    String result = rs.getString(1);
                    if(result.length() < 10){
                        return accountNumberFormatter(result);
                    }
                    throw  new SQLException("Account number must be exactly 10 characters long", result);

                }
                finally {
                    try {
                        sharedSessionContractImplementor.getJdbcCoordinator().getLogicalConnection().getResourceRegistry().release(rs, st);
                    }
                    catch (Throwable ignore){
                        // intentionally empty
                    }
                }
            }finally {
                sharedSessionContractImplementor.getJdbcCoordinator().getLogicalConnection().getResourceRegistry().release(st);
                sharedSessionContractImplementor.getJdbcCoordinator().afterStatementExecution();
            }
        }catch (SQLException sqlException){
            if(sqlException.getMessage().equals("Account number must be exactly 10 characters long"))
                throw sharedSessionContractImplementor.getJdbcServices().getSqlExceptionHelper().convert(
                        sqlException,
                        "Account number must be exactly 10 characters long",
                        sqlException.getSQLState()
                );
            throw sharedSessionContractImplementor.getJdbcServices().getSqlExceptionHelper().convert(
                    sqlException,
                    "could not get next sequence value",
                    sqlSelectFrag
            );
        }
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return EventTypeSets.INSERT_ONLY;
    }

    private String accountNumberFormatter(String accnumber){
        int length = accnumber.length();
        StringBuilder builder = new StringBuilder(accnumber);
        while (length < 10){
            builder = builder.insert(0, 0);
            ++length;
        }
        return  builder.toString();
    }
}
