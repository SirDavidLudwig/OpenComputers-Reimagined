package tech.dlii.opencomputers.api.network.node;

import tech.dlii.opencomputers.api.machine.architecture.Callback;
import tech.dlii.opencomputers.api.machine.Context;
import tech.dlii.opencomputers.api.network.Visibility;

import java.util.Collection;

public interface ComponentNode {
    /**
     * The name of the node.
     * <br>
     * This should be the type name of the component represented by the node,
     * since this is what is returned from {@code component.type}. As such it
     * is to be expected that there be multiple nodes with the same name, but
     * that those nodes all have the same underlying type (i.e. there can be
     * multiple "filesystem" nodes, but they should all behave the same way).
     */
    String name();

    /**
     * Get the visibility of this component.
     */
    Visibility visibility();

    /**
     * Set the visibility of this component.
     * <br>
     * Note that this cannot be higher / more visible than the reachability of
     * the node. Trying to set it to a higher value will generate an exception.
     *
     * @throws java.lang.IllegalArgumentException if the specified value is
     *                                            more visible than the node's
     *                                            reachability.
     */
    void setVisibility(Visibility value);

    /**
     * Tests whether this component can be seen by the specified node,
     * usually representing a computer in the network.
     * <br>
     * <em>Important</em>: this will always return {@code true} if the node is
     * not currently in a network.
     *
     * @param other the computer node to check for.
     * @return true if the computer can see this node; false otherwise.
     */
    boolean canBeSeenFrom(Node other);

    // ----------------------------------------------------------------------- //

    /**
     * The list of names of methods exposed by this component.
     * <br>
     * This does not return the callback annotations directly, because those
     * may not contain the method's name (as it defaults to the name of the
     * annotated method).
     * <br>
     * The returned collection is read-only.
     */
    Collection<String> methods();

    /**
     * Get the annotation information of a method.
     * <br>
     * This is needed for custom architecture implementations that need to know
     * if a callback is direct or not, for example.
     *
     * @param method the method to the the info for.
     * @return the annotation of the specified method or {@code null}.
     */
    Callback annotation(String method);

    /**
     * Tries to call a function with the specified name on this component.
     * <br>
     * The name of the method must be one of the names in {@link #methods()}.
     * The returned array may be {@code null} if there is no return value.
     *
     * @param method    the name of the method to call.
     * @param context   the context from which the method is called, usually the
     *                  instance of the computer running the script that made
     *                  the call.
     * @param arguments the arguments passed to the method.
     * @return the list of results, or {@code null} if there is no result.
     * @throws NoSuchMethodException if there is no method with that name.
     */
    Object[] invoke(String method, Context context, Object... arguments) throws Exception;
}
