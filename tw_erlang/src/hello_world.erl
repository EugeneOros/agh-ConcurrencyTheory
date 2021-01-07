
-module(hello_world).

-export([start/0, a_func/2, b_func/2, c1_func/0, c2_func/0, c3_func/0]).

a_func(C_PID, -1) ->
  io:format("A finished~n");

a_func(C_PID, N) ->
  C_PID ! {aaa, N},
  a_func(C_PID, N+1).

b_func(C_PID, -1) ->
  io:format("B finished~n");

b_func(C_PID, N) ->
  C_PID ! {bbb, N},
  b_func(C_PID, N+1).

c1_func() ->
  receive
    {aaa, N} -> io:format("aaa from A received. Iteration: ~p ~n", [N])
  end,
  receive
    {bbb, N} -> io:format("bbb from B received. Iteration: ~p ~n", [N])
  end,
  c1_func().

c2_func() ->
  receive
    {aaa, N} -> io:format("aaa from A received. Iteration: ~p ~n ", [N]);
    {bbb, N} -> io:fwrite("bbb from B received. Iteration: ~p ~n", [N])
  end,
  c2_func().

c3_func() ->
  receive
    {_, N} -> io:format("received. Iteration: ~p ~n", [N])
  end,
  c3_func().


start() ->
  C_PID = spawn(hello_world, c1_func, []),
  spawn(hello_world, a_func, [C_PID, 100]),
  spawn(hello_world, b_func, [C_PID, 100]).
























%%%%%-------------------------------------------------------------------
%%%%% @author yevhe
%%%%% @copyright (C) 2020, <COMPANY>
%%%%% @doc
%%%%%
%%%%% @end
%%%%% Created : 10. Dec 2020 00:11
%%%%%-------------------------------------------------------------------
%%-module(hello_world).
%%-author("yevhe").
%%
%%%% API
%%-export([start/0, c1_func/0, c1/0 a_func/1, b_func/2] ).
%%
%%c1_func() ->
%%  receive
%%    bbb ->
%%      io:format("C received bbb", []);
%%    aaa ->
%%      io:format("C received aaa", [])
%%  end,
%%  c1(0).
%%
%%c1() ->
%%  receive
%%    aaa ->
%%      io:format("C received aaa", []);
%%    bbb ->
%%      io:format("C received bbb", [])
%%  end,
%%  c1_func(1).
%%
%%a_func(C_PID) ->
%%  receive
%%    {b, B_PID}->
%%      C_PID ! aaa,
%%      B_PID ! b
%%  end.
%%
%%b_func(C_PID, A_PID) ->
%%  A_PID ! {b, self()},
%%  receive
%%    b ->
%%      C_PID ! bbb
%%  end.
%%
%%start() ->
%%  C_PID = spawn(hello_world, c1, []),
%%  A_PID = spawn(hello_world, a_func, [C_PID]),
%%  spawn(hello_world, b_func, [C_PID, A_PID]).
%%
%%
%%


%%ping(0, Pong_PID) ->
%%  Pong_PID ! finished,
%%  io:format("ping finished~n", []);
%%
%%ping(N, Pong_PID) ->
%%  Pong_PID ! {ping, self()},
%%  receive
%%    pong ->
%%      io:format("Ping received pong~n", [])
%%  end,
%%  ping(N - 1, Pong_PID).
%%
%%pong() ->
%%  receive
%%    finished ->
%%      io:format("Pong finished~n", []);
%%    {ping, Ping_PID} ->
%%      io:format("Pong received ping~n", []),
%%      Ping_PID ! pong,
%%      pong()
%%  end.

%%start() ->
%%  Pong_PID = spawn(hello_world, pong, []),
%%  spawn(hello_world, ping, [3, Pong_PID]).

%%
%%hello() ->
%%  io:fwrite("pop").
