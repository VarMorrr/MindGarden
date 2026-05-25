package com.example.mindgarden.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class FlowerDao_Impl implements FlowerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FlowerEntity> __insertionAdapterOfFlowerEntity;

  public FlowerDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFlowerEntity = new EntityInsertionAdapter<FlowerEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `flowers` (`id`,`type`,`taskName`,`durationMinutes`,`completedAt`,`isAlive`,`hourOfDay`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FlowerEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getType());
        statement.bindString(3, entity.getTaskName());
        statement.bindLong(4, entity.getDurationMinutes());
        statement.bindLong(5, entity.getCompletedAt());
        final int _tmp = entity.isAlive() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindLong(7, entity.getHourOfDay());
      }
    };
  }

  @Override
  public Object insert(final FlowerEntity flower, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfFlowerEntity.insertAndReturnId(flower);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<FlowerEntity>> getAllFlowers() {
    final String _sql = "SELECT * FROM flowers ORDER BY completedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"flowers"}, new Callable<List<FlowerEntity>>() {
      @Override
      @NonNull
      public List<FlowerEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTaskName = CursorUtil.getColumnIndexOrThrow(_cursor, "taskName");
          final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsAlive = CursorUtil.getColumnIndexOrThrow(_cursor, "isAlive");
          final int _cursorIndexOfHourOfDay = CursorUtil.getColumnIndexOrThrow(_cursor, "hourOfDay");
          final List<FlowerEntity> _result = new ArrayList<FlowerEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FlowerEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpTaskName;
            _tmpTaskName = _cursor.getString(_cursorIndexOfTaskName);
            final int _tmpDurationMinutes;
            _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            final long _tmpCompletedAt;
            _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            final boolean _tmpIsAlive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsAlive);
            _tmpIsAlive = _tmp != 0;
            final int _tmpHourOfDay;
            _tmpHourOfDay = _cursor.getInt(_cursorIndexOfHourOfDay);
            _item = new FlowerEntity(_tmpId,_tmpType,_tmpTaskName,_tmpDurationMinutes,_tmpCompletedAt,_tmpIsAlive,_tmpHourOfDay);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<FlowerEntity>> getAliveFlowers() {
    final String _sql = "SELECT * FROM flowers WHERE isAlive = 1 ORDER BY completedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"flowers"}, new Callable<List<FlowerEntity>>() {
      @Override
      @NonNull
      public List<FlowerEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTaskName = CursorUtil.getColumnIndexOrThrow(_cursor, "taskName");
          final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfIsAlive = CursorUtil.getColumnIndexOrThrow(_cursor, "isAlive");
          final int _cursorIndexOfHourOfDay = CursorUtil.getColumnIndexOrThrow(_cursor, "hourOfDay");
          final List<FlowerEntity> _result = new ArrayList<FlowerEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FlowerEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpTaskName;
            _tmpTaskName = _cursor.getString(_cursorIndexOfTaskName);
            final int _tmpDurationMinutes;
            _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            final long _tmpCompletedAt;
            _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            final boolean _tmpIsAlive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsAlive);
            _tmpIsAlive = _tmp != 0;
            final int _tmpHourOfDay;
            _tmpHourOfDay = _cursor.getInt(_cursorIndexOfHourOfDay);
            _item = new FlowerEntity(_tmpId,_tmpType,_tmpTaskName,_tmpDurationMinutes,_tmpCompletedAt,_tmpIsAlive,_tmpHourOfDay);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Integer> getAliveCount() {
    final String _sql = "SELECT COUNT(*) FROM flowers WHERE isAlive = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"flowers"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Integer> getTotalMinutes() {
    final String _sql = "SELECT COALESCE(SUM(durationMinutes), 0) FROM flowers WHERE isAlive = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"flowers"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<HourStat>> getMinutesByHour() {
    final String _sql = "\n"
            + "        SELECT hourOfDay, COALESCE(SUM(durationMinutes), 0) as total\n"
            + "        FROM flowers\n"
            + "        WHERE isAlive = 1\n"
            + "        GROUP BY hourOfDay\n"
            + "        ORDER BY hourOfDay\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"flowers"}, new Callable<List<HourStat>>() {
      @Override
      @NonNull
      public List<HourStat> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfHourOfDay = 0;
          final int _cursorIndexOfTotal = 1;
          final List<HourStat> _result = new ArrayList<HourStat>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HourStat _item;
            final int _tmpHourOfDay;
            _tmpHourOfDay = _cursor.getInt(_cursorIndexOfHourOfDay);
            final int _tmpTotal;
            _tmpTotal = _cursor.getInt(_cursorIndexOfTotal);
            _item = new HourStat(_tmpHourOfDay,_tmpTotal);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<DayStat>> getMinutesByDayOfWeek() {
    final String _sql = "\n"
            + "        SELECT \n"
            + "            CAST(strftime('%w', datetime(completedAt / 1000, 'unixepoch')) AS INTEGER) as dayOfWeek,\n"
            + "            COALESCE(SUM(durationMinutes), 0) as total\n"
            + "        FROM flowers\n"
            + "        WHERE isAlive = 1\n"
            + "        GROUP BY dayOfWeek\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"flowers"}, new Callable<List<DayStat>>() {
      @Override
      @NonNull
      public List<DayStat> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDayOfWeek = 0;
          final int _cursorIndexOfTotal = 1;
          final List<DayStat> _result = new ArrayList<DayStat>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DayStat _item;
            final int _tmpDayOfWeek;
            _tmpDayOfWeek = _cursor.getInt(_cursorIndexOfDayOfWeek);
            final int _tmpTotal;
            _tmpTotal = _cursor.getInt(_cursorIndexOfTotal);
            _item = new DayStat(_tmpDayOfWeek,_tmpTotal);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
