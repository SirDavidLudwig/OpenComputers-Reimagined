package tech.dlii.opencomputers.api.network.node;

import tech.dlii.opencomputers.api.network.Environment;
import tech.dlii.opencomputers.api.network.Network;
import tech.dlii.opencomputers.api.network.Visibility;

import java.util.List;

/**
 * A single node in a {@link Network}.
 * <br>
 * All nodes in a network have a unique address; the network will generate a
 * unique address and assign it to new nodes.
 * <br>
 * Per default there are two kinds of nodes: tile entities and items.
 * <br>
 * Items will usually only have nodes when in containers, such as a computer or
 * disk drive. Otherwise you'll have to connect/disconnect them manually as
 * desired.
 * <br>
 * All other kinds of nodes you may come up with will also have to be
 * handled manually.
 * <br>
 * Items have to be handled by a corresponding {@link DriverItem}.
 * Existing blocks may be interfaced with the adapter block if a
 * {@link DriverBlock} exists that supports the block.
 * <br>
 * <em>Important</em>: like the {@link Network} interface you must not create
 * your own implementations of this interface. Use the factory methods in the
 * network API to create new node instances and store them in your environment.
 *
 * @see Component
 */
public interface Node {
    /**
     * The environment hosting this node.
     * <br>
     * For blocks whose tile entities implement {@link Environment} this will
     * be the tile entity. For all other implementations this will be a managed
     * environment.
     */
    Environment host();

    /**
     * The reachability of this node.
     * <br>
     * This is used by the network to control which system messages to deliver
     * to which nodes. This value should not change over the lifetime of a node.
     * <br>
     * It furthermore determines what is returned by the {@link Network}'s
     * {@code neighbors} and {@code nodes} functions.
     * <br>
     * Note that this has no effect on the <em>real</em> reachability of a node;
     * it is only used to filter to which nodes to send connect, disconnect and
     * reconnect messages. If addressed directly, the node will still receive
     * that message even if it comes from a node that should not be able to see
     * it; therefore, nodes should still verify themselves that they want to
     * accept a message from the message's source.
     * <br>
     * A different matter is a {@link Component}'s {@code visibility}, which is
     * checked before delivering messages a computer tries to send.
     */
    Visibility reachability();

    /**
     * The address of the node, so that it can be found in the network.
     * <br>
     * This is used by the network manager when a node is added to a network to
     * assign it a unique address, if it doesn't already have one. Nodes must not
     * use custom addresses, only those assigned by the network. The only option
     * they have is to *not* have an address, which can be useful for "dummy"
     * nodes, such as cables. In that case they may ignore the address being set.
     */
    String address();

    /**
     * The network this node is currently in.
     * <br>
     * Note that valid nodes should never return `None` here. When created, a node
     * should immediately be added to a network, after being removed from its
     * network a node should be considered invalid.
     * <br>
     * This will always be set automatically by the network manager. Do not
     * change this value and do not return anything that it wasn't set to.
     */
    Network network();

    // ----------------------------------------------------------------------- //

    /**
     * Checks whether this node is a neighbor of the specified node.
     *
     * @param other the node to check for.
     * @return whether this node is directly connected to the other node.
     */
    boolean isNeighborOf(Node other);

    /**
     * Checks whether this node can be reached from the specified node.
     *
     * @param other the node to check for.
     * @return whether this node can be reached from the specified node.
     */
    boolean canBeReachedFrom(Node other);

    /**
     * Get the list of neighbor nodes, i.e. nodes directly connected to this
     * node.
     * <br>
     * This is a shortcut for {@code node.network.neighbors(node)}.
     * <br>
     * If this node is not in a network, i.e. {@code network} is {@code null},
     * this returns an empty list.
     *
     * @return the list of nodes directly connected to this node.
     */
    default Iterable<Node> neighbors() {
        if (network() == null) {
            return List.of();
        }
        return network().neighbors(this);
    }

    /**
     * Get the list of nodes reachable from this node, based on their
     * {@link #reachability()}.
     * <br>
     * This is a shortcut for {@code node.network.nodes(node)}.
     * <br>
     * If this node is not in a network, i.e. {@code network} is {@code null},
     * this returns an empty list.
     *
     * @return the list of nodes reachable from this node.
     */
    default Iterable<Node> reachableNodes() {
        if (network() == null) {
            return List.of();
        }
        return network().nodes(this);
    }

    // ----------------------------------------------------------------------- //

    /**
     * Connects the specified node to this node.
     * <br>
     * This is a shortcut for {@code node.network.connect(node, other)}.
     * <br>
     * If this node is not in a network, i.e. {@code network} is {@code null},
     * this will throw an exception.
     *
     * @param node the node to connect to this node.
     * @throws NullPointerException if {@code network} is {@code null}.
     */
    default void connect(Node node) throws NullPointerException {
        if (network() == null) {
            throw new NullPointerException("network is null");
        }
        network().connect(this, node);
    }

    /**
     * Disconnects the specified node from this node.
     * <br>
     * This is a shortcut for {@code node.network.disconnect(node, other)}.
     * <br>
     * If this node is not in a network, i.e. {@code network} is {@code null},
     * this will do nothing.
     *
     * @param node the node to connect to this node.
     * @throws NullPointerException if {@code network} is {@code null}.
     */
    default void disconnect(Node node) throws NullPointerException {
        if (network() == null) {
            throw new NullPointerException("network is null");
        }
        network().disconnect(this, node);
    }

    /**
     * Removes this node from its network.
     * <br>
     * This is a shortcut for {@code node.network.remove(node)}.
     * <br>
     * If this node is not in a network, i.e. {@code network} is {@code null},
     * this will do nothing.
     */
    default void remove() {
        if (network() == null) {
            return;
        }
        network().remove(this);
    }

    // ----------------------------------------------------------------------- //

    /**
     * Send a message to a node with the specified address.
     * <br>
     * This is a shortcut for {@code node.network.sendToAddress(node, ...)}.
     * <br>
     * If this node is not in a network, i.e. {@code network} is {@code null},
     * this will do nothing.
     *
     * @param target the address of the node to send the message to.
     * @param name   the name of the message.
     * @param data   the data to pass along with the message.
     */
    default void sendToAddress(String target, String name, Object... data) {
        if (network() == null) {
            return;
        }
        network().sendToAddress(this, target, name, data);
    }

    /**
     * Send a message to all neighbors of this node.
     * <br>
     * This is a shortcut for {@code node.network.sendToNeighbors(node, ...)}.
     * <br>
     * If this node is not in a network, i.e. {@code network} is {@code null},
     * this will do nothing.
     *
     * @param name the name of the message.
     * @param data the data to pass along with the message.
     */
    default void sendToNeighbors(String name, Object... data) {
        if (network() == null) {
            return;
        }
        network().sendToNeighbors(this, name, data);
    }

    /**
     * Send a message to all nodes reachable from this node.
     * <br>
     * This is a shortcut for {@code node.network.sendToReachable(node, ...)}.
     * <br>
     * If this node is not in a network, i.e. {@code network} is {@code null},
     * this will do nothing.
     *
     * @param name the name of the message.
     * @param data the data to pass along with the message.
     */
    default void sendToReachable(String name, Object... data) {
        if (network() == null) {
            return;
        }
        network().sendToReachable(this, name, data);
    }

    /**
     * Send a message to all nodes visible from this node.
     * <br>
     * This is a shortcut for {@code node.network.sendToVisible(node, ...)}.
     * <br>
     * If this node is not in a network, i.e. {@code network} is {@code null},
     * this will do nothing.
     *
     * @param name the name of the message.
     * @param data the data to pass along with the message.
     */
    default void sendToVisible(String name, Object... data) {
        if (network() == null) {
            return;
        }
        network().sendToVisible(this, name, data);
    }

    public static interface Builder {
        public Node build();
    }
}