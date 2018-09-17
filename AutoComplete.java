/**
 *
 */
package spelling;

import java.util.List;

/**
 * @author Jiayi Li
 *
 */
public interface AutoComplete {
	public List<String> predictCompletions(String prefix, int numCompletions);
}
